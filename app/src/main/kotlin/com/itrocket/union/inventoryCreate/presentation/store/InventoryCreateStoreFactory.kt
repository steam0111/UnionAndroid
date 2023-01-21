package com.itrocket.union.inventoryCreate.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.comment.domain.CommentInteractor
import com.itrocket.union.comment.presentation.store.CommentResult
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.InventoryInteractor
import com.itrocket.union.inventory.domain.entity.InventoryNomenclatureDomain
import com.itrocket.union.inventory.presentation.store.InventoryResult
import com.itrocket.union.inventoryChoose.domain.InventoryChooseActionType
import com.itrocket.union.inventoryChoose.presentation.store.InventoryChooseResult
import com.itrocket.union.inventoryCreate.domain.InventoryCreateInteractor
import com.itrocket.union.inventoryCreate.domain.InventoryDynamicSaveManager
import com.itrocket.union.inventoryCreate.domain.entity.AccountingObjectCounter
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.moduleSettings.domain.ModuleSettingsInteractor
import com.itrocket.union.readingMode.domain.ReadingModeInteractor
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.readingMode.presentation.view.toReadingMode
import com.itrocket.union.search.SearchManager
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class InventoryCreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoryCreateInteractor: InventoryCreateInteractor,
    private val inventoryCreateArguments: InventoryCreateArguments,
    private val inventoryInteractor: InventoryInteractor,
    private val errorInteractor: ErrorInteractor,
    private val serviceEntryManager: ServiceEntryManager,
    private val searchManager: SearchManager,
    private val unionPermissionsInteractor: UnionPermissionsInteractor,
    private val inventoryDynamicSaveManager: InventoryDynamicSaveManager,
    private val moduleSettingsInteractor: ModuleSettingsInteractor,
    private val commentInteractor: CommentInteractor,
    private val readingModeInteractor: ReadingModeInteractor
) {
    fun create(): InventoryCreateStore =
        object : InventoryCreateStore,
            Store<InventoryCreateStore.Intent, InventoryCreateStore.State, InventoryCreateStore.Label> by storeFactory.create(
                name = "InventoryCreateStore",
                initialState = InventoryCreateStore.State(
                    inventoryDocument = inventoryCreateArguments.inventoryDocument
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<InventoryCreateStore.Intent, Unit, InventoryCreateStore.State, Result, InventoryCreateStore.Label> =
        InventoryCreateExecutor()

    private inner class InventoryCreateExecutor :
        BaseExecutor<InventoryCreateStore.Intent, Unit, InventoryCreateStore.State, Result, InventoryCreateStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> InventoryCreateStore.State
        ) {
            dispatch(Result.Loading(true))
            dispatch(Result.ReadingMode(moduleSettingsInteractor.getDefaultReadingMode(isForceUpdate = true)))
            dispatch(Result.CanUpdate(unionPermissionsInteractor.canUpdate(UnionPermission.INVENTORY)))
            dispatch(
                Result.CanComplete(
                    unionPermissionsInteractor.canCompleteInventory(
                        UnionPermission.INVENTORY
                    )
                )
            )
            catchException {
                val inventory = inventoryCreateInteractor.getInventoryById(
                    id = inventoryCreateArguments.inventoryDocument.id.orEmpty(),
                    isAccountingObjectLoad = true
                )
                val isDynamicSaveInventory = moduleSettingsInteractor.getDynamicSaveInventory()
                val accountingObjects = inventory.accountingObjects
                val isExistNonMarkingAccountingObject =
                    inventoryInteractor.isExistNonMarkingAccountingObjects(
                        accountingObjects
                    )
                dispatch(Result.Inventory(inventory))
                dispatch(Result.IsDynamicSaveInventory(isDynamicSaveInventory))
                dispatch(Result.IsExistNonMarkingAccountingObjects(isExistNonMarkingAccountingObject))
            }
            dispatch(Result.Loading(false))
            if (getState().isDynamicSaveInventory && getState().inventoryDocument.inventoryStatus != InventoryStatus.COMPLETED) {
                inventoryDynamicSaveManager.subscribeInventorySave()
            }
            searchManager.listenSearch { searchText ->
                listenInventoryObjects(
                    searchText = searchText,
                    accountingObjects = getState().inventoryDocument.accountingObjects,
                    inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords,
                    getState = getState
                )
            }
        }

        override suspend fun executeIntent(
            intent: InventoryCreateStore.Intent,
            getState: () -> InventoryCreateStore.State
        ) {
            when (intent) {
                InventoryCreateStore.Intent.OnBackClicked -> onBackClicked(
                    getState().isShowSearch,
                    getState().dialogType
                )

                is InventoryCreateStore.Intent.OnAccountingObjectClicked -> publish(
                    InventoryCreateStore.Label.ShowAccountingObjectDetail(intent.accountingObject)
                )

                InventoryCreateStore.Intent.OnAddNewClicked -> {
                    dispatch(Result.AddNew(!getState().isAddNew))
                }

                InventoryCreateStore.Intent.OnDropClicked -> {
                    dispatch(Result.DialogType(AlertType.DROP))

                }

                InventoryCreateStore.Intent.OnDropConfirmed -> {
                    drop(getState)
                }

                InventoryCreateStore.Intent.OnHideFoundAccountingObjectClicked -> {
                    dispatch(Result.HideFoundedAccountingObjects(!getState().isHideFoundAccountingObjects))
                }

                InventoryCreateStore.Intent.OnReadingClicked -> {
                    publish(InventoryCreateStore.Label.ShowReadingMode)
                }

                InventoryCreateStore.Intent.OnSaveClicked -> dispatch(Result.DialogType(AlertType.SAVE))
                is InventoryCreateStore.Intent.OnNewBarcodeHandled -> {
                    val inventoryStatus = getState().inventoryDocument.inventoryStatus
                    if (inventoryStatus != InventoryStatus.COMPLETED && getState().canUpdate) {
                        handleNewAccountingObjectBarcode(
                            accountingObjects = getState().inventoryDocument.accountingObjects,
                            barcode = intent.barcode,
                            inventoryStatus = inventoryStatus,
                            isAddNew = getState().isAddNew,
                            isShowSearch = getState().isShowSearch,
                            searchText = getState().searchText,
                            readingModeTab = getState().readingModeTab,
                            getState = getState,
                            inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
                        )
                    }
                }

                is InventoryCreateStore.Intent.OnNewRfidHandled -> {
                    val inventoryStatus = getState().inventoryDocument.inventoryStatus
                    if (inventoryStatus != InventoryStatus.COMPLETED && getState().canUpdate) {
                        handleNewRfids(
                            accountingObjects = getState().inventoryDocument.accountingObjects,
                            handledRfids = intent.handledRfids,
                            inventoryStatus = inventoryStatus,
                            isAddNew = getState().isAddNew,
                            isShowSearch = getState().isShowSearch,
                            searchText = getState().searchText,
                            getState = getState,
                            inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
                        )
                    }
                }

                InventoryCreateStore.Intent.OnCompleteClicked -> dispatch(
                    Result.DialogType(
                        AlertType.COMPLETE
                    )
                )

                is InventoryCreateStore.Intent.OnSaveDismissed -> dispatch(
                    Result.DialogType(
                        AlertType.NONE
                    )
                )

                is InventoryCreateStore.Intent.OnSaveConfirmed -> {
                    dispatch(Result.DialogType(AlertType.NONE))
                    saveInventory(
                        inventoryDocument = getState().inventoryDocument,
                        accountingObjects = getState().inventoryDocument.accountingObjects,
                        inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
                    )
                }

                is InventoryCreateStore.Intent.OnReadingModeTabChanged -> dispatch(
                    Result.ReadingMode(
                        intent.readingModeTab
                    )
                )

                is InventoryCreateStore.Intent.OnManualInput -> onManualInput(
                    readingModeResult = intent.readingModeResult,
                    getState = getState
                )

                InventoryCreateStore.Intent.OnSearchClicked -> {
                    dispatch(Result.IsShowSearch(true))
                    listenInventoryObjects(
                        searchText = "",
                        accountingObjects = getState().inventoryDocument.accountingObjects,
                        inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords,
                        getState = getState
                    )
                }

                is InventoryCreateStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }

                InventoryCreateStore.Intent.OnCompleteConfirmed -> {
                    dispatch(Result.DialogType(AlertType.NONE))
                    completeInventory(inventoryDomain = getState().inventoryDocument)
                }

                InventoryCreateStore.Intent.OnCompleteDismissed -> dispatch(
                    Result.DialogType(
                        AlertType.NONE
                    )
                )

                InventoryCreateStore.Intent.OnDeleteConfirmClicked -> onDeleteConfirmed(getState)
                InventoryCreateStore.Intent.OnDeleteDismissClicked -> dispatch(
                    Result.DialogType(
                        AlertType.NONE
                    )
                )

                is InventoryCreateStore.Intent.OnStatusClicked -> onStatusClicked(
                    getState = getState,
                    accountingObject = intent.accountingObject
                )

                is InventoryCreateStore.Intent.OnAccountingObjectChanged -> {
                    onAccountingObjectChanged(
                        getState = getState,
                        accountingObject = intent.accountingObject
                    )
                }

                is InventoryCreateStore.Intent.OnAccountingObjectLongClicked -> publish(
                    InventoryCreateStore.Label.ShowInventoryChoose(intent.accountingObject)
                )

                is InventoryCreateStore.Intent.OnInventoryChooseResultHandled -> onInventoryChooseResultHandled(
                    intent.result
                )

                is InventoryCreateStore.Intent.OnCommentResultHandled -> onCommentResultHandled(
                    result = intent.result,
                    getState = getState
                )

                is InventoryCreateStore.Intent.OnErrorHandled -> handleError(intent.throwable)
                is InventoryCreateStore.Intent.OnExitConfirmed -> {
                    publish(InventoryCreateStore.Label.GoBack(InventoryResult(true)))
                }

                is InventoryCreateStore.Intent.OnAlertDismissed -> dispatch(
                    Result.DialogType(AlertType.NONE)
                )
                is InventoryCreateStore.Intent.OnInventoryNomenclatureClicked -> onInventoryNomenclatureClicked(
                    intent.inventoryNomenclature
                )
            }
        }

        private fun onInventoryNomenclatureClicked(inventoryNomenclature: InventoryNomenclatureDomain) {
            publish(
                InventoryCreateStore.Label.ShowSelectCount(
                    id = inventoryNomenclature.id,
                    count = (inventoryNomenclature.actualCount ?: 0).toBigDecimal()
                )
            )
        }

        private suspend fun onCommentResultHandled(
            result: CommentResult,
            getState: () -> InventoryCreateStore.State
        ) {
            val inventoryAccountingObjects = commentInteractor.changeAccountingObjectComment(
                accountingObjectId = result.entityId,
                comment = result.comment,
                listAccountingObject = getState().inventoryDocument.accountingObjects,
            )
            changeAccountingObjects(inventoryAccountingObjects)

            tryDynamicSendInventorySave(
                getState = getState,
                accountingObjects = getState().inventoryDocument.accountingObjects,
                inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
            )
        }

        private fun onInventoryChooseResultHandled(result: InventoryChooseResult) {
            when (result.type) {
                InventoryChooseActionType.SHOW_AO -> publish(
                    InventoryCreateStore.Label.ShowAccountingObjectDetail(
                        result.accountingObject
                    )
                )

                InventoryChooseActionType.ADD_COMMENT -> publish(
                    InventoryCreateStore.Label.ShowComment(
                        result.accountingObject
                    )
                )
            }
        }

        private suspend fun onAccountingObjectChanged(
            getState: () -> InventoryCreateStore.State,
            accountingObject: AccountingObjectDomain
        ) {
            readingModeInteractor.changeScanMode(getState().readingModeTab.toReadingMode())
            val inventoryAccountingObjects = inventoryCreateInteractor.changeAccountingObject(
                accountingObjects = getState().inventoryDocument.accountingObjects,
                accountingObject = accountingObject
            )
            changeAccountingObjects(inventoryAccountingObjects)
        }

        private suspend fun onDeleteConfirmed(getState: () -> InventoryCreateStore.State) {
            dispatch(Result.DialogType(AlertType.NONE))

            val inventoryAccountingObjects = inventoryCreateInteractor.removeAccountingObject(
                accountingObjects = getState().inventoryDocument.accountingObjects,
                accountingObjectId = getState().dialogRemovedItemId
            )
            changeAccountingObjects(inventoryAccountingObjects)

            dispatch(Result.DialogRemovedItemId(""))
            updateInventoryCounter(getState)
            tryDynamicSendInventorySave(
                getState = getState,
                accountingObjects = getState().inventoryDocument.accountingObjects,
                inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
            )
        }

        private suspend fun onStatusClicked(
            getState: () -> InventoryCreateStore.State,
            accountingObject: AccountingObjectDomain,
        ) {
            if (accountingObject.inventoryStatus != InventoryAccountingObjectStatus.NEW) {
                changeAccountingObjects(
                    inventoryCreateInteractor.changeStatus(
                        accountingObjects = getState().inventoryDocument.accountingObjects,
                        accountingObjectId = accountingObject.id
                    )
                )
                if (getState().isShowSearch) {
                    dispatch(
                        Result.SearchAccountingObjects(
                            inventoryCreateInteractor.changeStatus(
                                accountingObjectId = accountingObject.id,
                                accountingObjects = getState().searchAccountingObjects
                            )
                        )
                    )
                }
                updateInventoryCounter(getState)
                tryDynamicSendInventorySave(
                    getState = getState,
                    accountingObjects = getState().inventoryDocument.accountingObjects,
                    inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
                )
            } else {
                dispatch(Result.DialogRemovedItemId(accountingObject.id))
                dispatch(Result.DialogType(AlertType.DELETE))
            }
        }

        private fun canEditInventory(state: InventoryCreateStore.State): Boolean {
            return with(state) {
                canUpdate && inventoryDocument.inventoryStatus != InventoryStatus.COMPLETED
            }
        }

        private fun tryDynamicSendInventorySave(
            getState: () -> InventoryCreateStore.State,
            accountingObjects: List<AccountingObjectDomain>,
            inventoryNomenclatures: List<InventoryNomenclatureDomain>
        ) {
            if (getState().isDynamicSaveInventory) {
                val inventory = getState().inventoryDocument
                inventoryDynamicSaveManager.sendInventoryDomain(
                    inventory.copy(
                        accountingObjects = accountingObjects,
                        nomenclatureRecords = inventoryNomenclatures
                    )
                )
            }
        }

        private suspend fun onBackClicked(isShowSearch: Boolean, dialogType: AlertType) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                dispatch(Result.SearchAccountingObjects(listOf()))
                searchManager.emit("")
            } else if (dialogType != AlertType.LOADING) {
                dispatch(Result.DialogType(AlertType.EXIT))
            }
        }

        private suspend fun onManualInput(
            readingModeResult: ReadingModeResult,
            getState: () -> InventoryCreateStore.State
        ) {
            when (readingModeResult.readingModeTab) {
                ReadingModeTab.RFID -> {
                    //no-op
                }

                ReadingModeTab.SN, ReadingModeTab.BARCODE -> {
                    handleNewAccountingObjectBarcode(
                        accountingObjects = getState().inventoryDocument.accountingObjects,
                        barcode = readingModeResult.scanData,
                        inventoryStatus = getState().inventoryDocument.inventoryStatus,
                        isAddNew = getState().isAddNew,
                        isShowSearch = getState().isShowSearch,
                        searchText = getState().searchText,
                        readingModeTab = getState().readingModeTab,
                        getState = getState,
                        inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
                    )
                }
            }
        }

        private suspend fun completeInventory(
            inventoryDomain: InventoryCreateDomain,
        ) {
            dispatch(Result.IsCompleteLoading(true))
            dispatch(Result.DialogType(AlertType.LOADING))
            inventoryDynamicSaveManager.cancel()

            val inventory = inventoryDomain.copy(inventoryStatus = InventoryStatus.COMPLETED)
            dispatch(Result.Inventory(inventory))
            dispatch(Result.IsDynamicSaveInventory(false))

            inventoryCreateInteractor.saveInventoryDocument(
                inventoryCreate = inventory,
                accountingObjects = inventory.accountingObjects,
                inventoryNomenclatures = inventory.nomenclatureRecords
            )

            dispatch(Result.IsCompleteLoading(false))
            dispatch(Result.DialogType(AlertType.NONE))
        }

        private suspend fun handleNewRfids(
            accountingObjects: List<AccountingObjectDomain>,
            inventoryNomenclatures: List<InventoryNomenclatureDomain>,
            handledRfids: List<String>,
            inventoryStatus: InventoryStatus,
            isAddNew: Boolean,
            isShowSearch: Boolean,
            searchText: String,
            getState: () -> InventoryCreateStore.State
        ) {
            if (canEditInventory(getState())) {
                dispatch(Result.Loading(true))
                catchException {
                    val scannedInventoryObjects =
                        inventoryCreateInteractor.handleNewRfids(
                            accountingObjects = accountingObjects,
                            handledRfids = handledRfids,
                            inventoryStatus = inventoryStatus,
                            isAddNew = isAddNew,
                            inventoryNomenclatures = inventoryNomenclatures,
                            nomenclatureExistRfids = getState().nomenclatureExistRfids
                        )
                    changeAccountingObjects(scannedInventoryObjects.accountingObjects)
                    changeInventoryNomenclatures(scannedInventoryObjects.inventoryNomenclatures)
                    dispatch(
                        Result.NomenclatureExistRfids(
                            inventoryCreateInteractor.addInventoryExistRfids(
                                existRfids = getState().nomenclatureExistRfids,
                                newRfids = handledRfids
                            )
                        )
                    )
                    if (scannedInventoryObjects.hasWrittenOffAccountingObjects) {
                        publish(
                            InventoryCreateStore.Label.ShowToast(
                                message = errorInteractor.getMessageByResId(
                                    R.string.inventory_create_written_off_error
                                ),
                                backgroundColor = R.color.psb1
                            )
                        )
                    }
                    if (isShowSearch) {
                        listenInventoryObjects(
                            searchText = searchText,
                            accountingObjects = scannedInventoryObjects.accountingObjects,
                            inventoryNomenclatures = scannedInventoryObjects.inventoryNomenclatures,
                            getState = getState
                        )
                    }
                    updateInventoryCounter(getState)
                    tryDynamicSendInventorySave(
                        getState = getState,
                        accountingObjects = getState().inventoryDocument.accountingObjects,
                        inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
                    )
                }
                dispatch(Result.Loading(false))
            }
        }

        private suspend fun handleNewAccountingObjectBarcode(
            accountingObjects: List<AccountingObjectDomain>,
            inventoryNomenclatures: List<InventoryNomenclatureDomain>,
            barcode: String,
            inventoryStatus: InventoryStatus,
            isAddNew: Boolean,
            isShowSearch: Boolean,
            searchText: String,
            readingModeTab: ReadingModeTab,
            getState: () -> InventoryCreateStore.State
        ) {
            if (canEditInventory(getState())) {
                dispatch(Result.Loading(true))
                catchException {
                    val scannedInventoryObjects =
                        inventoryCreateInteractor.handleNewBarcode(
                            accountingObjects = accountingObjects,
                            barcode = barcode,
                            inventoryStatus = inventoryStatus,
                            isAddNew = isAddNew,
                            isSerialNumber = readingModeTab == ReadingModeTab.SN,
                            inventoryNomenclatures = inventoryNomenclatures
                        )
                    if (scannedInventoryObjects.hasWrittenOffAccountingObjects) {
                        publish(
                            InventoryCreateStore.Label.ShowToast(
                                message = errorInteractor.getMessageByResId(
                                    R.string.inventory_create_written_off_error
                                ),
                                backgroundColor = R.color.psb1
                            )
                        )
                    }
                    changeAccountingObjects(scannedInventoryObjects.accountingObjects)
                    changeInventoryNomenclatures(scannedInventoryObjects.inventoryNomenclatures)
                    if (isShowSearch) {
                        listenInventoryObjects(
                            searchText = searchText,
                            accountingObjects = scannedInventoryObjects.accountingObjects,
                            inventoryNomenclatures = scannedInventoryObjects.inventoryNomenclatures,
                            getState = getState
                        )
                    }
                    updateInventoryCounter(getState)
                    tryDynamicSendInventorySave(
                        getState = getState,
                        accountingObjects = getState().inventoryDocument.accountingObjects,
                        inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
                    )
                }
                dispatch(Result.Loading(false))
            }
        }

        private suspend fun saveInventory(
            inventoryDocument: InventoryCreateDomain,
            inventoryNomenclatures: List<InventoryNomenclatureDomain>,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            dispatch(Result.DialogType(AlertType.LOADING))
            catchException {
                inventoryCreateInteractor.saveInventoryDocument(
                    inventoryCreate = inventoryDocument,
                    accountingObjects = accountingObjects,
                    inventoryNomenclatures = inventoryNomenclatures
                )
            }
            dispatch(Result.DialogType(AlertType.NONE))
        }

        private suspend fun drop(getState: () -> InventoryCreateStore.State) {
            val accountingObjects = inventoryCreateInteractor.updateAccountingObjectListAfterDrop(
                oldList = getState().inventoryDocument.accountingObjects
            )
            changeAccountingObjects(accountingObjects)
            dispatch(Result.DialogType(AlertType.NONE))
            updateInventoryCounter(getState)
        }

        private suspend fun listenInventoryObjects(
            searchText: String,
            accountingObjects: List<AccountingObjectDomain>,
            inventoryNomenclatures: List<InventoryNomenclatureDomain>,
            getState: () -> InventoryCreateStore.State
        ) {
            dispatch(Result.Loading(true))
            catchException {
                dispatch(
                    Result.SearchAccountingObjects(
                        inventoryCreateInteractor.searchAccountingObjects(
                            searchText = searchText,
                            accountingObjects = accountingObjects,
                        )
                    )
                )

                dispatch(
                    Result.SearchInventoryNomenclatures(
                        inventoryCreateInteractor.searchInventoryNomenclatures(
                            searchText = searchText,
                            inventoryNomenclatures = inventoryNomenclatures
                        )
                    )
                )
            }
            updateInventoryCounter(getState)
            dispatch(Result.Loading(false))
        }

        private suspend fun updateInventoryCounter(getState: () -> InventoryCreateStore.State) {
            val accountingObjects = getState().inventoryDocument.accountingObjects
            val inventoryNomenclatures = getState().inventoryDocument.nomenclatureRecords
            dispatch(
                Result.CountOfInventoryObjects(
                    inventoryObjectCounter = inventoryCreateInteractor.getInventoryObjectCount(
                        accountingObjects = accountingObjects,
                        inventoryNomenclatures = inventoryNomenclatures
                    )
                )
            )
        }

        private suspend fun changeAccountingObjects(accountingObjects: List<AccountingObjectDomain>) {
            dispatch(Result.AccountingObjects(accountingObjects))
            dispatch(
                Result.IsExistNonMarkingAccountingObjects(
                    inventoryInteractor.isExistNonMarkingAccountingObjects(
                        accountingObjects
                    )
                )
            )
        }

        private fun changeInventoryNomenclatures(inventoryNomenclatures: List<InventoryNomenclatureDomain>) {
            dispatch(Result.InventoryNomenclatures(inventoryNomenclatures))
        }

        override fun handleError(throwable: Throwable) {
            publish(InventoryCreateStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Inventory(val inventory: InventoryCreateDomain) : Result()
        data class AddNew(val addNew: Boolean) : Result()
        data class HideFoundedAccountingObjects(val hideFoundedAccountingObjects: Boolean) :
            Result()

        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class InventoryNomenclatures(val inventoryNomenclatures: List<InventoryNomenclatureDomain>) :
            Result()

        data class DialogType(val dialogType: AlertType) : Result()
        data class ReadingMode(val readingModeTab: ReadingModeTab) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class SearchAccountingObjects(val searchAccountingObjects: List<AccountingObjectDomain>) :
            Result()

        data class SearchInventoryNomenclatures(val searchInventoryNomenclatures: List<InventoryNomenclatureDomain>) :
            Result()

        data class DialogRemovedItemId(val accountingObjectId: String) : Result()
        data class IsDynamicSaveInventory(val isDynamicSaveInventory: Boolean) : Result()
        data class CanUpdate(val canUpdate: Boolean) : Result()
        data class CanComplete(val canComplete: Boolean) : Result()
        data class CountOfInventoryObjects(
            val inventoryObjectCounter: AccountingObjectCounter
        ) : Result()

        data class IsCompleteLoading(val isLoading: Boolean) : Result()
        data class IsExistNonMarkingAccountingObjects(val isExistNonMarkingAccountingObject: Boolean) :
            Result()

        data class NomenclatureExistRfids(val nomenclatureExistRfids: List<String>) : Result()
    }

    private object ReducerImpl : Reducer<InventoryCreateStore.State, Result> {
        override fun InventoryCreateStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.AddNew -> copy(isAddNew = result.addNew)
                is Result.HideFoundedAccountingObjects -> copy(isHideFoundAccountingObjects = result.hideFoundedAccountingObjects)
                is Result.AccountingObjects -> copy(
                    inventoryDocument = inventoryDocument.copy(
                        accountingObjects = result.accountingObjects
                    )
                )

                is Result.Inventory -> copy(inventoryDocument = result.inventory)
                is Result.ReadingMode -> copy(readingModeTab = result.readingModeTab)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchAccountingObjects -> copy(searchAccountingObjects = result.searchAccountingObjects)
                is Result.CanUpdate -> copy(canUpdate = result.canUpdate)
                is Result.IsDynamicSaveInventory -> copy(isDynamicSaveInventory = result.isDynamicSaveInventory)
                is Result.CountOfInventoryObjects -> copy(inventoryObjectCounter = result.inventoryObjectCounter)
                is Result.DialogType -> copy(dialogType = result.dialogType)
                is Result.DialogRemovedItemId -> copy(dialogRemovedItemId = result.accountingObjectId)
                is Result.CanComplete -> copy(canComplete = result.canComplete)
                is Result.IsCompleteLoading -> copy(isCompleteLoading = result.isLoading)
                is Result.IsExistNonMarkingAccountingObjects -> copy(
                    isExistNonMarkingAccountingObject = result.isExistNonMarkingAccountingObject
                )
                is Result.InventoryNomenclatures -> copy(
                    inventoryDocument = inventoryDocument.copy(
                        nomenclatureRecords = result.inventoryNomenclatures
                    )
                )
                is Result.SearchInventoryNomenclatures -> copy(searchInventoryNomenclatures = result.searchInventoryNomenclatures)
                is Result.NomenclatureExistRfids -> copy(
                    inventoryDocument = inventoryDocument.copy(
                        rfids = result.nomenclatureExistRfids
                    )
                )
            }
    }
}
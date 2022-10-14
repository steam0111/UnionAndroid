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
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventoryCreate.domain.InventoryCreateInteractor
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectArguments
import com.itrocket.union.readingMode.presentation.store.ReadingModeResult
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.readingMode.presentation.view.toReadingModeTab
import com.itrocket.union.search.SearchManager
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import com.itrocket.union.utils.ifBlankOrNull
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class InventoryCreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoryCreateInteractor: InventoryCreateInteractor,
    private val inventoryCreateArguments: InventoryCreateArguments,
    private val errorInteractor: ErrorInteractor,
    private val serviceEntryManager: ServiceEntryManager,
    private val searchManager: SearchManager,
    private val unionPermissionsInteractor: UnionPermissionsInteractor,
) {
    fun create(): InventoryCreateStore =
        object : InventoryCreateStore,
            Store<InventoryCreateStore.Intent, InventoryCreateStore.State, InventoryCreateStore.Label> by storeFactory.create(
                name = "InventoryCreateStore",
                initialState = InventoryCreateStore.State(
                    inventoryDocument = inventoryCreateArguments.inventoryDocument,
                    readingModeTab = serviceEntryManager.currentMode.toReadingModeTab()
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
            dispatch(Result.CanUpdate(unionPermissionsInteractor.canUpdate(UnionPermission.INVENTORY)))
            catchException {
                dispatch(
                    Result.Inventory(
                        inventoryCreateInteractor.getInventoryById(
                            inventoryCreateArguments.inventoryDocument.id.orEmpty()
                        )
                    )
                )
            }
            dispatch(Result.Loading(false))
            searchManager.listenSearch {
                listenAccountingObjects(
                    searchText = it,
                    accountingObjects = getState().inventoryDocument.accountingObjects,
                    newAccountingObjects = getState().newAccountingObjects.toList()
                )
            }
        }

        override suspend fun executeIntent(
            intent: InventoryCreateStore.Intent,
            getState: () -> InventoryCreateStore.State
        ) {
            when (intent) {
                InventoryCreateStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                is InventoryCreateStore.Intent.OnAccountingObjectClicked -> handleAccountingObjectClicked(
                    accountingObjects = getState().inventoryDocument.accountingObjects,
                    accountingObject = intent.accountingObject
                )
                InventoryCreateStore.Intent.OnAddNewClicked -> {
                    dispatch(Result.AddNew(!getState().isAddNew))
                }
                InventoryCreateStore.Intent.OnDropClicked -> {
                    drop(getState())
                }
                InventoryCreateStore.Intent.OnHideFoundAccountingObjectClicked -> {
                    dispatch(Result.HideFoundedAccountingObjects(!getState().isHideFoundAccountingObjects))
                }
                InventoryCreateStore.Intent.OnReadingClicked -> {
                    publish(InventoryCreateStore.Label.ShowReadingMode)
                }
                InventoryCreateStore.Intent.OnSaveClicked -> {
                    dispatch(Result.ConfirmDialogVisibility(true))
                }
                is InventoryCreateStore.Intent.OnNewAccountingObjectBarcodeHandled -> {
                    val inventoryStatus = getState().inventoryDocument.inventoryStatus
                    if (inventoryStatus != InventoryStatus.COMPLETED && getState().canUpdate) {
                        handleNewAccountingObjectBarcode(
                            accountingObjects = getState().inventoryDocument.accountingObjects,
                            newAccountingObjects = getState().newAccountingObjects.toList(),
                            barcode = intent.barcode,
                            inventoryStatus = inventoryStatus,
                            isAddNew = getState().isAddNew,
                            isShowSearch = getState().isShowSearch,
                            searchText = getState().searchText
                        )
                    }
                }
                is InventoryCreateStore.Intent.OnAccountingObjectStatusChanged -> {
                    changeAccountingObjectsStatus(
                        getState = getState,
                        switcherResult = intent.switcherResult.result
                    )
                }
                is InventoryCreateStore.Intent.OnNewAccountingObjectRfidHandled -> {
                    val inventoryStatus = getState().inventoryDocument.inventoryStatus
                    if (inventoryStatus != InventoryStatus.COMPLETED && getState().canUpdate) {
                        handleNewAccountingObjectRfids(
                            accountingObjects = getState().inventoryDocument.accountingObjects,
                            newAccountingObjects = getState().newAccountingObjects.toList(),
                            handledAccountingObjectId = intent.handledAccountingObjectId,
                            inventoryStatus = inventoryStatus,
                            isAddNew = getState().isAddNew,
                            isShowSearch = getState().isShowSearch,
                            searchText = getState().searchText
                        )
                    }
                }
                InventoryCreateStore.Intent.OnCompleteClicked -> completeInventory(
                    inventoryDomain = getState().inventoryDocument,
                    newAccountingObjects = getState().newAccountingObjects.toList()
                )
                is InventoryCreateStore.Intent.OnDismissConfirmDialog -> {
                    dispatch(Result.ConfirmDialogVisibility(false))
                }
                is InventoryCreateStore.Intent.OnConfirmActionClick -> {
                    dispatch(Result.ConfirmDialogVisibility(false))
                    saveInventory(
                        inventoryDocument = getState().inventoryDocument,
                        accountingObjects = getState().inventoryDocument.accountingObjects + getState().newAccountingObjects
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
                    listenAccountingObjects(
                        searchText = "",
                        accountingObjects = getState().inventoryDocument.accountingObjects,
                        newAccountingObjects = getState().newAccountingObjects.toList()
                    )
                }
                is InventoryCreateStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
            }
        }

        private suspend fun changeAccountingObjectsStatus(
            getState: () -> InventoryCreateStore.State,
            switcherResult: SwitcherDomain
        ) {
            dispatch(
                Result.AccountingObjects(
                    inventoryCreateInteractor.changeAccountingObjectInventoryStatus(
                        getState().inventoryDocument.accountingObjects,
                        switcherResult
                    )
                )
            )
            if (getState().isShowSearch) {
                dispatch(
                    Result.SearchAccountingObjects(
                        inventoryCreateInteractor.changeAccountingObjectInventoryStatus(
                            getState().searchAccountingObjects,
                            switcherResult
                        )
                    )
                )
            }
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                dispatch(Result.SearchAccountingObjects(listOf()))
                searchManager.emit("")
            } else {
                publish(InventoryCreateStore.Label.GoBack)
            }
        }

        private suspend fun onManualInput(
            readingModeResult: ReadingModeResult,
            getState: () -> InventoryCreateStore.State
        ) {
            val inventoryStatus = getState().inventoryDocument.inventoryStatus
            if (inventoryStatus != InventoryStatus.COMPLETED && getState().canUpdate) {
                when (readingModeResult.readingModeTab) {
                    ReadingModeTab.RFID, ReadingModeTab.SN -> {
                        //no-op
                    }
                    ReadingModeTab.BARCODE -> {
                        handleNewAccountingObjectBarcode(
                            accountingObjects = getState().inventoryDocument.accountingObjects,
                            newAccountingObjects = getState().newAccountingObjects.toList(),
                            barcode = readingModeResult.scanData,
                            inventoryStatus = getState().inventoryDocument.inventoryStatus,
                            isAddNew = getState().isAddNew,
                        isShowSearch = getState().isShowSearch,
                        searchText = getState().searchText)
                    }
                }
            }
        }

        private suspend fun completeInventory(
            inventoryDomain: InventoryCreateDomain,
            newAccountingObjects: List<AccountingObjectDomain>
        ) {
            val inventory = inventoryDomain.copy(
                inventoryStatus = InventoryStatus.COMPLETED,
                accountingObjects = newAccountingObjects + inventoryDomain.accountingObjects
            )
            dispatch(Result.NewAccountingObjects(setOf()))
            dispatch(Result.Inventory(inventory))
            inventoryCreateInteractor.saveInventoryDocument(inventory, inventory.accountingObjects)
        }

        private suspend fun handleNewAccountingObjectRfids(
            accountingObjects: List<AccountingObjectDomain>,
            handledAccountingObjectId: String,
            newAccountingObjects: List<AccountingObjectDomain>,
            inventoryStatus: InventoryStatus,
            isAddNew: Boolean,
            isShowSearch: Boolean,
            searchText: String
        ) {
            dispatch(Result.Loading(true))
            catchException {
                val inventoryAccountingObjects =
                    inventoryCreateInteractor.handleNewAccountingObjectRfids(
                        accountingObjects = accountingObjects,
                        handledAccountingObjectId = handledAccountingObjectId,
                        inventoryStatus = inventoryStatus,
                        isAddNew = isAddNew,
                        existNewAccountingObjects = newAccountingObjects
                    )
                dispatch(Result.AccountingObjects(inventoryAccountingObjects.createdAccountingObjects))
                val newInventoryAccountingObjects =
                    newAccountingObjects + inventoryAccountingObjects.newAccountingObjects
                dispatch(
                    Result.NewAccountingObjects((newInventoryAccountingObjects).toSet())
                )
                if (isShowSearch) {
                    listenAccountingObjects(
                        searchText = searchText,
                        accountingObjects = inventoryAccountingObjects.createdAccountingObjects,
                        newAccountingObjects = newInventoryAccountingObjects
                    )
                }
            }
            dispatch(Result.Loading(true))
        }

        private suspend fun handleNewAccountingObjectBarcode(
            accountingObjects: List<AccountingObjectDomain>,
            barcode: String,
            newAccountingObjects: List<AccountingObjectDomain>,
            inventoryStatus: InventoryStatus,
            isAddNew: Boolean,
            isShowSearch: Boolean,
            searchText: String
        ) {
            dispatch(Result.Loading(true))
            catchException {
                val inventoryAccountingObjects =
                    inventoryCreateInteractor.handleNewAccountingObjectBarcode(
                        accountingObjects = accountingObjects,
                        barcode = barcode,
                        inventoryStatus = inventoryStatus,
                        isAddNew = isAddNew,
                        existNewAccountingObjects = newAccountingObjects
                    )
                dispatch(Result.AccountingObjects(inventoryAccountingObjects.createdAccountingObjects))
                val newInventoryAccountingObjects =
                    newAccountingObjects + inventoryAccountingObjects.newAccountingObjects
                dispatch(
                    Result.NewAccountingObjects((newInventoryAccountingObjects).toSet())
                )
                if (isShowSearch) {
                    listenAccountingObjects(
                        searchText = searchText,
                        accountingObjects = inventoryAccountingObjects.createdAccountingObjects,
                        newAccountingObjects = newInventoryAccountingObjects
                    )
                }
            }
            dispatch(Result.Loading(true))
        }

        private fun handleAccountingObjectClicked(
            accountingObjects: List<AccountingObjectDomain>,
            accountingObject: AccountingObjectDomain
        ) {
            if (!inventoryCreateInteractor.isNewAccountingObject(
                    accountingObjects = accountingObjects,
                    newAccountingObject = accountingObject
                )
            ) {
                publish(
                    InventoryCreateStore.Label.ShowChangeStatus(
                        SwitcherDomain(
                            titleId = R.string.switcher_accounting_object_status,
                            values = listOf(
                                InventoryAccountingObjectStatus.NOT_FOUND,
                                InventoryAccountingObjectStatus.FOUND
                            ),
                            currentValue = accountingObject.inventoryStatus,
                            entityId = accountingObject.id
                        )
                    )
                )
            } else {
                publish(
                    InventoryCreateStore.Label.ShowNewAccountingObjectDetail(
                        NewAccountingObjectArguments(
                            accountingObject.id
                        )
                    )
                )
            }
        }

        private suspend fun saveInventory(
            inventoryDocument: InventoryCreateDomain,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            dispatch(Result.Loading(true))
            catchException {
                inventoryCreateInteractor.saveInventoryDocument(
                    inventoryDocument,
                    accountingObjects
                )
                publish(InventoryCreateStore.Label.GoBack)
            }
            dispatch(Result.Loading(false))
        }

        private fun drop(state: InventoryCreateStore.State) {
            dispatch(Result.NewAccountingObjects(setOf()))
            dispatch(
                Result.AccountingObjects(
                    inventoryCreateInteractor.dropAccountingObjects(
                        state.inventoryDocument.accountingObjects
                    )
                )
            )
            dispatch(
                Result.SearchAccountingObjects(
                    inventoryCreateInteractor.dropAccountingObjects(state.searchAccountingObjects)
                )
            )
            dispatch(Result.AddNew(false))
            dispatch(Result.HideFoundedAccountingObjects(false))
        }

        private suspend fun listenAccountingObjects(
            searchText: String,
            accountingObjects: List<AccountingObjectDomain>,
            newAccountingObjects: List<AccountingObjectDomain>
        ) {
            dispatch(Result.Loading(true))
            catchException {
                dispatch(
                    Result.SearchAccountingObjects(
                        inventoryCreateInteractor.searchAccountingObjects(
                            searchText = searchText,
                            accountingObjects = accountingObjects,
                            newAccountingObject = newAccountingObjects
                        )
                    )
                )
            }
            dispatch(Result.Loading(false))
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
        data class NewAccountingObjects(val newAccountingObjects: Set<AccountingObjectDomain>) :
            Result()

        data class ConfirmDialogVisibility(val isVisible: Boolean) : Result()
        data class ReadingMode(val readingModeTab: ReadingModeTab) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class SearchAccountingObjects(val searchAccountingObjects: List<AccountingObjectDomain>) :
            Result()
        data class CanUpdate(val canUpdate: Boolean) : Result()
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
                is Result.NewAccountingObjects -> copy(newAccountingObjects = result.newAccountingObjects)
                is Result.Inventory -> copy(inventoryDocument = result.inventory)
                is Result.ConfirmDialogVisibility -> copy(isConfirmDialogVisible = result.isVisible)
                is Result.ReadingMode -> copy(readingModeTab = result.readingModeTab)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchAccountingObjects -> copy(searchAccountingObjects = result.searchAccountingObjects)
                is Result.CanUpdate -> copy(canUpdate = result.canUpdate)
            }
    }
}
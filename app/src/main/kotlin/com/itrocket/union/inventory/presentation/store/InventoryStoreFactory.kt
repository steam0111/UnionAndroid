package com.itrocket.union.inventory.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.InventoryInteractor
import com.itrocket.union.inventoryCreate.domain.InventoryCreateInteractor
import com.itrocket.union.inventoryCreate.domain.InventoryDynamicSaveManager
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.filterNotEmpty
import com.itrocket.union.moduleSettings.domain.ModuleSettingsInteractor
import com.itrocket.union.selectParams.domain.SelectParamsInteractor
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission

class InventoryStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoryInteractor: InventoryInteractor,
    private val accountingObjectInteractor: AccountingObjectInteractor,
    private val errorInteractor: ErrorInteractor,
    private val selectParamsInteractor: SelectParamsInteractor,
    private val filterInteractor: FilterInteractor,
    private val permissionsInteractor: UnionPermissionsInteractor,
    private val inventoryCreateDomain: InventoryCreateDomain?,
    private val inventoryCreateInteractor: InventoryCreateInteractor,
    private val inventoryDynamicSaveManager: InventoryDynamicSaveManager,
    private val moduleSettingsInteractor: ModuleSettingsInteractor
) {
    fun create(): InventoryStore =
        object : InventoryStore,
            Store<InventoryStore.Intent, InventoryStore.State, InventoryStore.Label> by storeFactory.create(
                name = "InventoryStore",
                initialState = InventoryStore.State(inventoryCreateDomain = inventoryCreateDomain),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<InventoryStore.Intent, Unit, InventoryStore.State, Result, InventoryStore.Label> =
        InventoryExecutor()

    private inner class InventoryExecutor :
        BaseExecutor<InventoryStore.Intent, Unit, InventoryStore.State, Result, InventoryStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> InventoryStore.State
        ) {
            val inventory = getState().inventoryCreateDomain?.let {
                inventoryCreateInteractor.getInventoryById(
                    requireNotNull(it.id)
                )
            }
            dispatch(Result.CanCreateInventory(permissionsInteractor.canCreate(UnionPermission.INVENTORY)))
            dispatch(Result.CanUpdateInventory(permissionsInteractor.canUpdate(UnionPermission.INVENTORY)))

            val isDynamicSaveInventory = moduleSettingsInteractor.getDynamicSaveInventory()
            dispatch(Result.IsDynamicSaveInventory(isDynamicSaveInventory))

            if (inventory != null) {
                dispatch(Result.InventoryCreate(inventory))
                dispatch(Result.Params(inventoryCreateInteractor.disableBalanceUnit(inventory.documentInfo)))
                observeAccountingObjects(getState, getState().params)

                if (getState().isDynamicSaveInventory) {
                    inventoryDynamicSaveManager.subscribeInventorySave()
                }
            } else {
                dispatch(Result.Params(selectParamsInteractor.getInitialDocumentParams(getState().params)))
                observeAccountingObjects(getState, getState().params)
            }
        }

        override suspend fun executeIntent(
            intent: InventoryStore.Intent,
            getState: () -> InventoryStore.State
        ) {
            when (intent) {
                InventoryStore.Intent.OnBackClicked -> publish(
                    InventoryStore.Label.GoBack(
                        InventoryResult(false)
                    )
                )
                InventoryStore.Intent.OnCreateDocumentClicked -> createInventory(
                    isDynamicSaveInventory = getState().isDynamicSaveInventory,
                    accountingObjects = getState().accountingObjectList,
                    params = getState().params
                )
                InventoryStore.Intent.OnDropClicked -> {
                    val params = inventoryInteractor.clearParams(getState().params)
                    changeParams(getState, params)
                }
                is InventoryStore.Intent.OnParamClicked -> showParams(
                    params = getState().params,
                    param = intent.param
                )
                is InventoryStore.Intent.OnParamCrossClicked -> {
                    val params = inventoryInteractor.clearParam(
                        getState().params,
                        intent.param
                    )
                    changeParams(getState, params)
                }
                is InventoryStore.Intent.OnParamsChanged -> {
                    val params = inventoryInteractor.changeParams(getState().params, intent.params)
                    changeParams(getState, params)
                }
                is InventoryStore.Intent.OnSelectPage -> dispatch(Result.SelectPage(intent.selectedPage))
                InventoryStore.Intent.OnInWorkClicked -> dispatch(Result.DialogType(AlertType.IN_WORK))
                InventoryStore.Intent.OnSaveClicked -> dispatch(Result.DialogType(AlertType.SAVE))
                InventoryStore.Intent.OnInWorkConfirmed -> {
                    inWorkInventory(
                        inventoryDomain = requireNotNull(getState().inventoryCreateDomain),
                        accountingObjects = getState().accountingObjectList,
                        params = getState().params
                    )
                    dispatch(Result.DialogType(AlertType.NONE))
                }
                InventoryStore.Intent.OnInWorkDismissed -> dispatch(Result.DialogType(AlertType.NONE))
                InventoryStore.Intent.OnSaveConfirmed -> {
                    saveInventory(
                        inventoryDocument = requireNotNull(getState().inventoryCreateDomain),
                        accountingObjects = getState().accountingObjectList,
                        params = getState().params
                    )
                    dispatch(Result.DialogType(AlertType.NONE))
                }
                InventoryStore.Intent.OnSaveDismissed -> dispatch(Result.DialogType(AlertType.NONE))
                is InventoryStore.Intent.OnAccountingObjectClicked -> publish(
                    InventoryStore.Label.ShowAccountingObjectDetail(
                        intent.accountingObject
                    )
                )
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(InventoryStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        private suspend fun changeParams(
            getState: () -> InventoryStore.State,
            params: List<ParamDomain>
        ) {
            dispatch(Result.Params(params))
            observeAccountingObjects(getState, params.filterNotEmpty())
        }

        private fun tryDynamicSendInventorySave(
            getState: () -> InventoryStore.State
        ) {
            val inventory = getState().inventoryCreateDomain
            if (getState().isDynamicSaveInventory && inventory != null) {
                inventoryDynamicSaveManager.sendInventoryDomain(
                    inventory.copy(
                        documentInfo = getState().params,
                        accountingObjects = getState().accountingObjectList
                    )
                )
            }
        }

        private fun showParams(params: List<ParamDomain>, param: ParamDomain) {
            publish(
                InventoryStore.Label.ShowParamSteps(
                    currentFilter = param,
                    allParams = params
                )
            )
        }

        private suspend fun saveInventory(
            inventoryDocument: InventoryCreateDomain,
            accountingObjects: List<AccountingObjectDomain>,
            params: List<ParamDomain>
        ) {
            dispatch(Result.IsAccountingObjectsLoading(true))
            catchException {
                inventoryCreateInteractor.saveInventoryDocument(
                    inventoryDocument.copy(documentInfo = params),
                    accountingObjects
                )
            }
            dispatch(Result.IsAccountingObjectsLoading(false))
        }

        private suspend fun inWorkInventory(
            inventoryDomain: InventoryCreateDomain,
            accountingObjects: List<AccountingObjectDomain>,
            params: List<ParamDomain>
        ) {
            val inventory = inventoryDomain.copy(
                inventoryStatus = InventoryStatus.IN_PROGRESS,
                accountingObjects = accountingObjects,
                documentInfo = params

            )
            inventoryCreateInteractor.saveInventoryDocument(
                inventoryCreate = inventory,
                accountingObjects = accountingObjects
            )
            inventoryDynamicSaveManager.cancel()
            publish(
                InventoryStore.Label.ShowCreateInventory(
                    inventoryCreate = inventory
                )
            )
        }

        private suspend fun createInventory(
            isDynamicSaveInventory: Boolean,
            accountingObjects: List<AccountingObjectDomain>,
            params: List<ParamDomain>
        ) {
            dispatch(Result.IsCreateInventoryLoading(true))
            catchException {
                val inventoryCreate =
                    inventoryInteractor.createInventory(accountingObjects, params)
                dispatch(Result.InventoryCreate(inventoryCreate))
            }
            if (isDynamicSaveInventory) {
                inventoryDynamicSaveManager.subscribeInventorySave()
            }
            dispatch(Result.IsCreateInventoryLoading(false))
        }

        private suspend fun observeAccountingObjects(
            getState: () -> InventoryStore.State,
            params: List<ParamDomain> = listOf()
        ) {
            dispatch(Result.IsAccountingObjectsLoading(true))
            catchException {
                dispatch(Result.IsAccountingObjectsLoading(false))
                dispatch(
                    Result.AccountingObjects(
                        accountingObjectInteractor.getAccountingObjects(
                            ManualType.LOCATION_INVENTORY,
                            "",
                            params
                        )
                    )
                )
                tryDynamicSendInventorySave(getState)
            }
            dispatch(Result.IsAccountingObjectsLoading(false))
        }
    }

    private sealed class Result {
        data class IsAccountingObjectsLoading(val isLoading: Boolean) : Result()
        data class IsCreateInventoryLoading(val isLoading: Boolean) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
        data class SelectPage(val page: Int) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class CanCreateInventory(val canCreateInventory: Boolean) : Result()
        data class CanUpdateInventory(val canUpdateInventory: Boolean) : Result()
        data class InventoryCreate(val inventoryCreateDomain: InventoryCreateDomain) : Result()
        data class IsDynamicSaveInventory(val isDynamicSaveInventory: Boolean) : Result()
        data class DialogType(val dialogType: AlertType) : Result()
    }

    private object ReducerImpl : Reducer<InventoryStore.State, Result> {
        override fun InventoryStore.State.reduce(result: Result) =
            when (result) {
                is Result.IsAccountingObjectsLoading -> copy(isAccountingObjectsLoading = result.isLoading)
                is Result.Params -> copy(params = result.params)
                is Result.SelectPage -> copy(selectedPage = result.page)
                is Result.AccountingObjects -> copy(accountingObjectList = result.accountingObjects)
                is Result.IsCreateInventoryLoading -> copy(isCreateInventoryLoading = result.isLoading)
                is Result.CanCreateInventory -> copy(canCreateInventory = result.canCreateInventory)
                is Result.InventoryCreate -> copy(inventoryCreateDomain = result.inventoryCreateDomain)
                is Result.CanUpdateInventory -> copy(canUpdateInventory = result.canUpdateInventory)
                is Result.IsDynamicSaveInventory -> copy(isDynamicSaveInventory = result.isDynamicSaveInventory)
                is Result.DialogType -> copy(dialogType = result.dialogType)
            }
    }
}
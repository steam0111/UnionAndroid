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
import com.itrocket.union.inventory.domain.InventoryInteractor
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.filterNotEmpty
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class InventoryStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoryInteractor: InventoryInteractor,
    private val accountingObjectInteractor: AccountingObjectInteractor
) {
    fun create(): InventoryStore =
        object : InventoryStore,
            Store<InventoryStore.Intent, InventoryStore.State, InventoryStore.Label> by storeFactory.create(
                name = "InventoryStore",
                initialState = InventoryStore.State(),
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
            observeAccountingObjects()
        }

        override suspend fun executeIntent(
            intent: InventoryStore.Intent,
            getState: () -> InventoryStore.State
        ) {
            when (intent) {
                InventoryStore.Intent.OnBackClicked -> publish(InventoryStore.Label.GoBack)
                InventoryStore.Intent.OnCreateDocumentClicked -> createInventory(
                    getState().accountingObjectList,
                    getState().params
                )
                InventoryStore.Intent.OnDropClicked -> {
                    val params = inventoryInteractor.clearParams(getState().params)
                    changeParams(params)
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
                    changeParams(params)
                }
                is InventoryStore.Intent.OnParamsChanged -> {
                    val params = inventoryInteractor.changeParams(getState().params, intent.params)
                    changeParams(params)
                }
                is InventoryStore.Intent.OnSelectPage -> dispatch(Result.SelectPage(intent.selectedPage))
                is InventoryStore.Intent.OnLocationChanged -> {
                    val params = inventoryInteractor.changeLocation(
                        getState().params,
                        intent.locationResult.location
                    )
                    changeParams(params)
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(InventoryStore.Label.Error(throwable.message.orEmpty()))
        }

        private suspend fun changeParams(params: List<ParamDomain>) {
            dispatch(Result.Params(params))
            isCreateEnabled(params)
            observeAccountingObjects(params.filterNotEmpty())
        }

        private fun isCreateEnabled(params: List<ParamDomain>) {
            dispatch(Result.IsCreateEnabled(inventoryInteractor.isParamsValid(params)))
        }

        private fun showParams(params: List<ParamDomain>, param: ParamDomain) {
            if (param.type == ManualType.LOCATION) {
                publish(
                    InventoryStore.Label.ShowLocation(param as LocationParamDomain)
                )
            } else {
                publish(
                    InventoryStore.Label.ShowParamSteps(
                        currentStep = params.indexOf(param) + 1,
                        params = params.filter { it.type != ManualType.LOCATION }
                    )
                )
            }
        }

        private suspend fun createInventory(
            accountingObjects: List<AccountingObjectDomain>,
            params: List<ParamDomain>
        ) {
            dispatch(Result.IsAccountingObjectsLoading(true))
            catchException {
                val inventoryCreate =
                    inventoryInteractor.createInventory(accountingObjects, params)
                publish(
                    InventoryStore.Label.ShowCreateInventory(
                        inventoryCreate = inventoryCreate
                    )
                )
            }
            dispatch(Result.IsAccountingObjectsLoading(false))
        }

        private suspend fun observeAccountingObjects(params: List<ParamDomain> = listOf()) {
            dispatch(Result.IsAccountingObjectsLoading(true))
            catchException {
                accountingObjectInteractor.getAccountingObjects(params)
                    .catch { dispatch(Result.IsAccountingObjectsLoading(false)) }
                    .collect {
                        dispatch(Result.AccountingObjects(it))
                        dispatch(Result.IsAccountingObjectsLoading(false))
                    }
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
        data class IsCreateEnabled(val enabled: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<InventoryStore.State, Result> {
        override fun InventoryStore.State.reduce(result: Result) =
            when (result) {
                is Result.IsAccountingObjectsLoading -> copy(isAccountingObjectsLoading = result.isLoading)
                is Result.Params -> copy(params = result.params)
                is Result.SelectPage -> copy(selectedPage = result.page)
                is Result.AccountingObjects -> copy(accountingObjectList = result.accountingObjects)
                is Result.IsCreateInventoryLoading -> copy(isCreateInventoryLoading = result.isLoading)
                is Result.IsCreateEnabled -> copy(isCreateEnabled = result.enabled)
            }
    }
}
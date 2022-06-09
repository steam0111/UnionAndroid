package com.itrocket.union.inventory.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.union.inventory.domain.InventoryInteractor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain

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
        }

        override suspend fun executeIntent(
            intent: InventoryStore.Intent,
            getState: () -> InventoryStore.State
        ) {
            when (intent) {
                InventoryStore.Intent.OnBackClicked -> publish(InventoryStore.Label.GoBack)
                InventoryStore.Intent.OnCreateDocumentClicked -> {
                    val inventoryCreate =
                        inventoryInteractor.createInventory(getState().accountingObjectList)
                    publish(
                        InventoryStore.Label.ShowCreateInventory(
                            inventoryCreate = inventoryCreate,
                            accountingObjectList = getState().accountingObjectList
                        )
                    )
                }
                InventoryStore.Intent.OnDropClicked -> {
                    dispatch(
                        Result.Params(
                            inventoryInteractor.clearParams(getState().params)
                        )
                    )
                    filterAccountingObjects(getState().params)
                }
                is InventoryStore.Intent.OnParamClicked -> {
                    if (intent.param.type == ManualType.LOCATION) {
                        publish(
                            InventoryStore.Label.ShowLocation(intent.param.value)
                        )
                    } else {
                        publish(
                            InventoryStore.Label.ShowParamSteps(
                                currentStep = getState().params.indexOf(intent.param) + 1,
                                params = getState().params.filter { it.type != ManualType.LOCATION }
                            )
                        )
                    }
                }
                is InventoryStore.Intent.OnParamCrossClicked -> {
                    dispatch(
                        Result.Params(
                            inventoryInteractor.clearParam(
                                getState().params,
                                intent.param
                            )
                        )
                    )
                    filterAccountingObjects(getState().params)
                }
                is InventoryStore.Intent.OnParamsChanged -> {
                    dispatch(
                        Result.Params(
                            inventoryInteractor.changeParams(getState().params, intent.params)
                        )
                    )
                    filterAccountingObjects(getState().params)
                }
                is InventoryStore.Intent.OnSelectPage -> dispatch(Result.SelectPage(intent.selectedPage))
                is InventoryStore.Intent.OnLocationChanged -> {
                    dispatch(
                        Result.Params(
                            inventoryInteractor.changeLocation(
                                getState().params,
                                intent.locationResult.location
                            )
                        )
                    )
                    filterAccountingObjects(getState().params)
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(InventoryStore.Label.Error(throwable.message.orEmpty()))
        }

        private suspend fun filterAccountingObjects(params: List<ParamDomain>) {
            dispatch(Result.Loading(true))
            dispatch(
                Result.AccountingObjects(
                    accountingObjectInteractor.getAccountingObjectsByParams(params)
                )
            )
            dispatch(Result.Loading(false))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
        data class SelectPage(val page: Int) : Result()
        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
    }

    private object ReducerImpl : Reducer<InventoryStore.State, Result> {
        override fun InventoryStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Params -> copy(params = result.params)
                is Result.SelectPage -> copy(selectedPage = result.page)
                is Result.AccountingObjects -> copy(accountingObjectList = result.accountingObjects)
            }
    }
}
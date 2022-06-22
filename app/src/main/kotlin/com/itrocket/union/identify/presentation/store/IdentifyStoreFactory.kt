package com.itrocket.union.identify.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.identify.domain.IdentifyInteractor
import com.itrocket.union.inventory.presentation.store.InventoryStoreFactory
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class IdentifyStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val identifyInteractor: IdentifyInteractor,
    private val filterInteractor: FilterInteractor,
    private val accountingObjectInteractor: AccountingObjectInteractor

//    private val accountingObjectDetailArguments: AccountingObjectDetailArguments

) {
    fun create(): IdentifyStore =
        object : IdentifyStore,
            Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> by storeFactory.create(
                name = "IdentifyStore",
                initialState = IdentifyStore.State(),
//                    accountingObjectDomain = accountingObjectDetailArguments.argument

                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<IdentifyStore.Intent, Unit, IdentifyStore.State, Result, IdentifyStore.Label> =
        IdentifyExecutor()

    private inner class IdentifyExecutor :
        BaseExecutor<IdentifyStore.Intent, Unit, IdentifyStore.State, Result, IdentifyStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> IdentifyStore.State
        ) {
            dispatch(Result.Loading(true))
            delay(1000)
//            dispatch(Result.Identify(identifyInteractor.getAccountingObjects()))
            dispatch(Result.Loading(false))
            observeAccountingObjects()
        }

        override suspend fun executeIntent(
            intent: IdentifyStore.Intent,
            getState: () -> IdentifyStore.State
        ) {
            when (intent) {
                IdentifyStore.Intent.OnBackClicked -> publish(IdentifyStore.Label.GoBack)
                IdentifyStore.Intent.OnSearchClicked -> publish(
                    IdentifyStore.Label.ShowSearch
                )
                IdentifyStore.Intent.OnFilterClicked -> publish(
                    IdentifyStore.Label.ShowFilter(filterInteractor.getFilters())
                )
                is IdentifyStore.Intent.OnItemClicked -> publish(
                    IdentifyStore.Label.ShowDetail(
                        intent.item
                    )
                )
                IdentifyStore.Intent.OnDropClicked -> {
                    TODO()
                }
                is IdentifyStore.Intent.OnSelectPage -> dispatch((Result.SelectPage(intent.selectedPage)))
            }
        }

        private suspend fun observeAccountingObjects(params: List<ParamDomain> = listOf()) {
            dispatch(Result.IsAccountingObjectsLoading(true))
            catchException {
                accountingObjectInteractor.getAccountingObjects(params)
                    .catch { dispatch(Result.IsAccountingObjectsLoading(false)) }
                    .collect {
                        dispatch(Result.Identify(it))
                        dispatch(Result.IsAccountingObjectsLoading(false))
                    }
            }
            dispatch(Result.IsAccountingObjectsLoading(false))
        }
    }


    private sealed class Result {
        data class IsAccountingObjectsLoading(val isLoading: Boolean) :
            IdentifyStoreFactory.Result()

        data class Loading(val isLoading: Boolean) : Result()
        data class Identify(val identifies: List<AccountingObjectDomain>) : Result()

        data class SelectPage(val page: Int) : IdentifyStoreFactory.Result()


    }

    private object ReducerImpl : Reducer<IdentifyStore.State, Result> {
        override fun IdentifyStore.State.reduce(result: Result): IdentifyStore.State =
            when (result) {
                is Result.Loading -> copy(isIdentifyLoading = result.isLoading)
                is Result.Identify -> copy(identifies = result.identifies)
                else -> copy(isIdentifyLoading = true)
            }
    }
}
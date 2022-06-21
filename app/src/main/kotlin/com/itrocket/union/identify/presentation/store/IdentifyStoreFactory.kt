package com.itrocket.union.identify.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailArguments
import com.itrocket.union.accountingObjects.domain.AccountingObjectInteractor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStoreFactory
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.identify.domain.entity.IdentifyDomain
import kotlinx.coroutines.delay

class IdentifyStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val identifyInteractor: AccountingObjectInteractor,
    private val filterInteractor: FilterInteractor,
//    private val accountingObjectDetailArguments: AccountingObjectDetailArguments

) {
    fun create(): IdentifyStore =
        object : IdentifyStore,
            Store<IdentifyStore.Intent, IdentifyStore.State, IdentifyStore.Label> by storeFactory.create(
                name = "IdentifyStore",
                initialState = IdentifyStore.State(
//                    accountingObjectDomain = accountingObjectDetailArguments.argument
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<IdentifyStore.Intent, Unit, IdentifyStore.State, Result, IdentifyStore.Label> =
        IdentifyExecutor()

    private inner class IdentifyExecutor :
        SuspendExecutor<IdentifyStore.Intent, Unit, IdentifyStore.State, Result, IdentifyStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> IdentifyStore.State
        ) {
            dispatch(Result.Loading(true))
            delay(1000)
            dispatch(Result.Identify(identifyInteractor.getAccountingObjects()))
            dispatch(Result.Loading(false))
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
//                is IdentifyStore.Intent.OnItemClicked -> publish(
//                    IdentifyStore.Label.ShowDetail(
//                        intent.item
//                    )
//                )
            }
        }
    }


    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Identify(val identifies: List<AccountingObjectDomain>) : IdentifyStoreFactory.Result()

    }

    private object ReducerImpl : Reducer<IdentifyStore.State, Result> {
        override fun IdentifyStore.State.reduce(result: Result): IdentifyStore.State =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                else -> copy(isLoading = true)
            }
    }
}
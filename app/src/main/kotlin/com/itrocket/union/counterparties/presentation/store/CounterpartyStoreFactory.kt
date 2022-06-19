package com.itrocket.union.counterparties.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.counterparties.domain.CounterpartyInteractor
import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class CounterpartyStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val counterpartyInteractor: CounterpartyInteractor
) {
    fun create(): CounterpartyStore =
        object : CounterpartyStore,
            Store<CounterpartyStore.Intent, CounterpartyStore.State, CounterpartyStore.Label> by storeFactory.create(
                name = "CounterpartyStore",
                initialState = CounterpartyStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<CounterpartyStore.Intent, Unit, CounterpartyStore.State, Result, CounterpartyStore.Label> =
        CounterpartyExecutor()

    private inner class CounterpartyExecutor :
        BaseExecutor<CounterpartyStore.Intent, Unit, CounterpartyStore.State, Result, CounterpartyStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> CounterpartyStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                counterpartyInteractor.getCounterparties()
                    .catch { dispatch(Result.Loading(false)) }
                    .collect {
                        dispatch(Result.Counterparties(it))
                        dispatch(Result.Loading(false))
                    }
            }
        }

        override suspend fun executeIntent(
            intent: CounterpartyStore.Intent,
            getState: () -> CounterpartyStore.State
        ) {
            when (intent) {
                CounterpartyStore.Intent.OnBackClicked -> publish(CounterpartyStore.Label.GoBack)
                is CounterpartyStore.Intent.OnCounterpartyClicked -> {
                }
                CounterpartyStore.Intent.OnFilterClicked -> {
                }
                CounterpartyStore.Intent.OnSearchClicked -> {
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(CounterpartyStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Counterparties(val counterparties: List<CounterpartyDomain>) : Result()
    }

    private object ReducerImpl : Reducer<CounterpartyStore.State, Result> {
        override fun CounterpartyStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Counterparties -> copy(counterparties = result.counterparties)
            }
    }
}
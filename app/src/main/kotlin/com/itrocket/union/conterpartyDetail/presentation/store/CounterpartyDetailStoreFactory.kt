package com.itrocket.union.conterpartyDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.conterpartyDetail.domain.CounterpartyDetailInteractor
import com.itrocket.union.conterpartyDetail.domain.entity.CounterpartyDetailDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.utils.ifBlankOrNull

class CounterpartyDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: CounterpartyDetailInteractor,
    private val args: CounterpartyDetailArguments,
    private val errorInteractor: ErrorInteractor
) {

    fun create(): CounterpartyDetailStore =
        object : CounterpartyDetailStore,
            Store<CounterpartyDetailStore.Intent, CounterpartyDetailStore.State, CounterpartyDetailStore.Label> by storeFactory.create(
                name = "CounterpartyDetailStore",
                initialState = CounterpartyDetailStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<CounterpartyDetailStore.Intent, Unit, CounterpartyDetailStore.State, Result, CounterpartyDetailStore.Label> =
        CounterpartyDetailExecutor()

    private inner class CounterpartyDetailExecutor :
        BaseExecutor<CounterpartyDetailStore.Intent, Unit, CounterpartyDetailStore.State, Result, CounterpartyDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> CounterpartyDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.CounterpartyDetail(
                        interactor.getCounterpartyDetail(args.id)
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(CounterpartyDetailStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        override suspend fun executeIntent(
            intent: CounterpartyDetailStore.Intent,
            getState: () -> CounterpartyDetailStore.State
        ) {
            when (intent) {
                CounterpartyDetailStore.Intent.OnBackClicked -> publish(CounterpartyDetailStore.Label.GoBack)
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class CounterpartyDetail(val item: CounterpartyDetailDomain) : Result()
    }

    private object ReducerImpl :
        Reducer<CounterpartyDetailStore.State, Result> {
        override fun CounterpartyDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.CounterpartyDetail -> copy(item = result.item)
            }
    }
}
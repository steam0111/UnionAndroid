package com.itrocket.union.producerDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.producerDetail.domain.ProducerDetailInteractor
import com.itrocket.union.producerDetail.domain.entity.ProducerDetailDomain

class ProducerDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: ProducerDetailInteractor,
    private val args: ProducerDetailArguments
) {

    fun create(): ProducerDetailStore =
        object : ProducerDetailStore,
            Store<ProducerDetailStore.Intent, ProducerDetailStore.State, ProducerDetailStore.Label> by storeFactory.create(
                name = "ProducerDetailStore",
                initialState = ProducerDetailStore.State(
                    item = ProducerDetailDomain(emptyList())
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ProducerDetailStore.Intent, Unit, ProducerDetailStore.State, Result, ProducerDetailStore.Label> =
        ProducerDetailExecutor()

    private inner class ProducerDetailExecutor :
        BaseExecutor<ProducerDetailStore.Intent, Unit, ProducerDetailStore.State, Result, ProducerDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ProducerDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.Producer(
                        interactor.getProducerDetail(args.id)
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(ProducerDetailStore.Label.Error(throwable.message.orEmpty()))
        }

        override suspend fun executeIntent(
            intent: ProducerDetailStore.Intent,
            getState: () -> ProducerDetailStore.State
        ) {
            when (intent) {
                ProducerDetailStore.Intent.OnBackClicked -> publish(
                    ProducerDetailStore.Label.GoBack
                )
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class Producer(val item: ProducerDetailDomain) : Result()
    }

    private object ReducerImpl :
        Reducer<ProducerDetailStore.State, Result> {
        override fun ProducerDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Producer -> copy(item = result.item)
            }
    }
}
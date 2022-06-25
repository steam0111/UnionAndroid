package com.itrocket.union.producer.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.producer.domain.ProducerInteractor
import com.itrocket.union.producer.domain.entity.ProducerDomain
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class ProducerStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val producerInteractor: ProducerInteractor,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): ProducerStore =
        object : ProducerStore,
            Store<ProducerStore.Intent, ProducerStore.State, ProducerStore.Label> by storeFactory.create(
                name = "ProducerStore",
                initialState = ProducerStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ProducerStore.Intent, Unit, ProducerStore.State, Result, ProducerStore.Label> =
        ProducerExecutor()

    private inner class ProducerExecutor :
        BaseExecutor<ProducerStore.Intent, Unit, ProducerStore.State, Result, ProducerStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ProducerStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                producerInteractor.getProducers()
                    .catch {
                        handleError(it)
                    }
                    .collect {
                        dispatch(Result.Producers(it))
                        dispatch(Result.Loading(false))
                    }
            }
        }

        override suspend fun executeIntent(
            intent: ProducerStore.Intent,
            getState: () -> ProducerStore.State
        ) {
            when (intent) {
                ProducerStore.Intent.OnBackClicked -> publish(ProducerStore.Label.GoBack)
                ProducerStore.Intent.OnFilterClicked -> {
                }
                is ProducerStore.Intent.OnProducerClicked -> {
                    publish(ProducerStore.Label.ShowDetail(intent.id))
                }
                ProducerStore.Intent.OnSearchClicked -> {
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(ProducerStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Producers(val producers: List<ProducerDomain>) : Result()
    }

    private object ReducerImpl : Reducer<ProducerStore.State, Result> {
        override fun ProducerStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Producers -> copy(producers = result.producers)
            }
    }
}
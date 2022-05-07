package com.itrocket.union.reserveDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.reserveDetail.domain.ReserveDetailInteractor
import com.itrocket.core.base.CoreDispatchers

class ReserveDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val reserveDetailInteractor: ReserveDetailInteractor,
    private val reserveDetailArguments: ReserveDetailArguments
) {
    fun create(): ReserveDetailStore =
        object : ReserveDetailStore,
            Store<ReserveDetailStore.Intent, ReserveDetailStore.State, ReserveDetailStore.Label> by storeFactory.create(
                name = "ReserveDetailStore",
                initialState = ReserveDetailStore.State(
                    reserve = reserveDetailArguments.argument
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ReserveDetailStore.Intent, Unit, ReserveDetailStore.State, Result, ReserveDetailStore.Label> =
        ReserveDetailExecutor()

    private inner class ReserveDetailExecutor :
        SuspendExecutor<ReserveDetailStore.Intent, Unit, ReserveDetailStore.State, Result, ReserveDetailStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ReserveDetailStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: ReserveDetailStore.Intent,
            getState: () -> ReserveDetailStore.State
        ) {
            when (intent) {
                ReserveDetailStore.Intent.OnBackClicked -> publish(
                    ReserveDetailStore.Label.GoBack
                )
                ReserveDetailStore.Intent.OnReadingModeClicked -> {
                    publish(ReserveDetailStore.Label.ShowReadingMode(getState().readingMode))
                }
                ReserveDetailStore.Intent.OnDocumentAddClicked -> {
                    //no-op
                }
                ReserveDetailStore.Intent.OnDocumentSearchClicked -> {
                    //no-op
                }
            }
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<ReserveDetailStore.State, Result> {
        override fun ReserveDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
            }
    }
}
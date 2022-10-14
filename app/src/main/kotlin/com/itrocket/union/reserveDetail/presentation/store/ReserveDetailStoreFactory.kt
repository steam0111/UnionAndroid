package com.itrocket.union.reserveDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.reserveDetail.domain.ReserveDetailInteractor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.utils.ifBlankOrNull

class ReserveDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val reserveDetailInteractor: ReserveDetailInteractor,
    private val reserveDetailArguments: ReserveDetailArguments,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): ReserveDetailStore =
        object : ReserveDetailStore,
            Store<ReserveDetailStore.Intent, ReserveDetailStore.State, ReserveDetailStore.Label> by storeFactory.create(
                name = "ReserveDetailStore",
                initialState = ReserveDetailStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ReserveDetailStore.Intent, Unit, ReserveDetailStore.State, Result, ReserveDetailStore.Label> =
        ReserveDetailExecutor()

    private inner class ReserveDetailExecutor :
        BaseExecutor<ReserveDetailStore.Intent, Unit, ReserveDetailStore.State, Result, ReserveDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ReserveDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.Reserve(
                        reserveDetailInteractor.getReserveById(
                            reserveDetailArguments.id
                        )
                    )
                )
                dispatch(Result.Loading(false))
            }
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

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(ReserveDetailStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Reserve(val reserve: ReservesDomain) : Result()
    }

    private object ReducerImpl : Reducer<ReserveDetailStore.State, Result> {
        override fun ReserveDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Reserve -> copy(reserve = result.reserve)
            }
    }
}
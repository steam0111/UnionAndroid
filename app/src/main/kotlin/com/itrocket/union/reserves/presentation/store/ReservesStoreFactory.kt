package com.itrocket.union.reserves.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.filter.domain.FilterInteractor
import com.itrocket.union.reserves.domain.ReservesInteractor
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.delay

class ReservesStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val reservesInteractor: ReservesInteractor,
    private val filterInteractor: FilterInteractor,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): ReservesStore =
        object : ReservesStore,
            Store<ReservesStore.Intent, ReservesStore.State, ReservesStore.Label> by storeFactory.create(
                name = "ReservesStore",
                initialState = ReservesStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ReservesStore.Intent, Unit, ReservesStore.State, Result, ReservesStore.Label> =
        ReservesExecutor()

    private inner class ReservesExecutor :
        BaseExecutor<ReservesStore.Intent, Unit, ReservesStore.State, Result, ReservesStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ReservesStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(Result.Reserves(reservesInteractor.getReserves()))
                dispatch(Result.Loading(false))
            }
        }

        override suspend fun executeIntent(
            intent: ReservesStore.Intent,
            getState: () -> ReservesStore.State
        ) {
            when (intent) {
                ReservesStore.Intent.OnBackClicked -> publish(ReservesStore.Label.GoBack)
                ReservesStore.Intent.OnSearchClicked -> publish(
                    ReservesStore.Label.ShowSearch
                )
                ReservesStore.Intent.OnFilterClicked -> publish(
                    ReservesStore.Label.ShowFilter(filterInteractor.getFilters())
                )
                is ReservesStore.Intent.OnItemClicked -> publish(
                    ReservesStore.Label.ShowDetail(intent.item)
                )
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(ReservesStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Reserves(val reserves: List<ReservesDomain>) : Result()
    }

    private object ReducerImpl : Reducer<ReservesStore.State, Result> {
        override fun ReservesStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Reserves -> copy(reserves = result.reserves)
            }
    }
}
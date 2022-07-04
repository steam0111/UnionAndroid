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
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.ReservesInteractor
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.delay

class ReservesStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val reservesInteractor: ReservesInteractor,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager,
    private val reservesArguments: ReservesArguments?
) {

    fun create(): ReservesStore =
        object : ReservesStore,
            Store<ReservesStore.Intent, ReservesStore.State, ReservesStore.Label> by storeFactory.create(
                name = "ReservesStore",
                initialState = ReservesStore.State(
                    params = reservesArguments?.params.orEmpty()
                ),
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
            searchManager.listenSearch {
                listenReserves(searchText = it, params = getState().params)
            }
        }

        override suspend fun executeIntent(
            intent: ReservesStore.Intent,
            getState: () -> ReservesStore.State
        ) {
            when (intent) {
                ReservesStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                ReservesStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                ReservesStore.Intent.OnFilterClicked -> publish(
                    ReservesStore.Label.ShowFilter(
                        getState().params.ifEmpty {
                            reservesInteractor.getFilters()
                        }
                    )
                )
                is ReservesStore.Intent.OnItemClicked -> onItemClicked(intent.item)
                ReservesStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is ReservesStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
                is ReservesStore.Intent.OnFilterResult -> {
                    dispatch(Result.Params(intent.params))
                    listenReserves(params = getState().params, searchText = getState().searchText)
                }
            }
        }

        private fun onItemClicked(item: ReservesDomain) {
            if (reservesArguments?.params.isNullOrEmpty()) {
                publish(
                    ReservesStore.Label.ShowDetail(item)
                )
            } else {
                publish(
                    ReservesStore.Label.GoBack(ReservesResult(item))
                )
            }
        }

        private suspend fun listenReserves(params: List<ParamDomain>, searchText: String) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.Reserves(
                        reservesInteractor.getReserves(
                            searchText = searchText,
                            params = params,
                            selectedReservesIds = reservesArguments?.selectedReservesIds.orEmpty()
                        )
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.emit("")
            } else {
                publish(ReservesStore.Label.GoBack())
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
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
    }

    private object ReducerImpl : Reducer<ReservesStore.State, Result> {
        override fun ReservesStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Reserves -> copy(reserves = result.reserves)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.Params -> copy(params = result.params)
            }
    }
}
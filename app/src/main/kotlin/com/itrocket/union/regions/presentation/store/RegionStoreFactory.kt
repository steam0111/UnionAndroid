package com.itrocket.union.regions.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.regions.domain.RegionInteractor
import com.itrocket.union.regions.domain.entity.RegionDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull

class RegionStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val regionInteractor: RegionInteractor,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {
    fun create(): RegionStore =
        object : RegionStore,
            Store<RegionStore.Intent, RegionStore.State, RegionStore.Label> by storeFactory.create(
                name = "RegionStore",
                initialState = RegionStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<RegionStore.Intent, Unit, RegionStore.State, Result, RegionStore.Label> =
        RegionExecutor()

    private inner class RegionExecutor :
        BaseExecutor<RegionStore.Intent, Unit, RegionStore.State, Result, RegionStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> RegionStore.State
        ) {
            searchManager.listenSearch {
                listenRegions(searchText = it)
            }
        }

        override suspend fun executeIntent(
            intent: RegionStore.Intent,
            getState: () -> RegionStore.State
        ) {
            when (intent) {
                RegionStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                RegionStore.Intent.OnFilterClicked -> {
                }
                is RegionStore.Intent.OnRegionClicked -> {
                    publish(RegionStore.Label.ShowDetail(intent.id))
                }
                RegionStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is RegionStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.searchQuery.emit(intent.searchText)
                }
            }
        }

        private suspend fun listenRegions(searchText: String = "") {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(Result.Regions(regionInteractor.getRegions(searchText)))
            }
            dispatch(Result.Loading(false))
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.searchQuery.emit("")
            } else {
                publish(RegionStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(RegionStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Regions(val regions: List<RegionDomain>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<RegionStore.State, Result> {
        override fun RegionStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Regions -> copy(regions = result.regions)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
            }
    }
}
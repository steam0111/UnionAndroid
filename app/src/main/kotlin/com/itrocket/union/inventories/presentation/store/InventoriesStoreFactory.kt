package com.itrocket.union.inventories.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.inventories.domain.InventoriesInteractor
import com.itrocket.union.inventory.data.mapper.toInventoryContainerType
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.paging.Paginator

class InventoriesStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoriesInteractor: InventoriesInteractor,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {
    fun create(): InventoriesStore =
        object : InventoriesStore,
            Store<InventoriesStore.Intent, InventoriesStore.State, InventoriesStore.Label> by storeFactory.create(
                name = "InventoriesStore",
                initialState = InventoriesStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<InventoriesStore.Intent, Unit, InventoriesStore.State, Result, InventoriesStore.Label> =
        InventoriesExecutor()

    private inner class InventoriesExecutor :
        BaseExecutor<InventoriesStore.Intent, Unit, InventoriesStore.State, Result, InventoriesStore.Label>(
            context = coreDispatchers.ui
        ) {

        private val paginator = Paginator<InventoryCreateDomain>(
            onError = {
                handleError(it)
            },
            onLoadUpdate = {
                dispatch(Result.Loading(it))
            },
            onSuccess = {
                dispatch(Result.Inventories(it))
            },
            onEndReached = {
                dispatch(Result.IsListEndReached(true))
            }
        )

        override suspend fun executeAction(
            action: Unit,
            getState: () -> InventoriesStore.State
        ) {
            searchManager.listenSearch { searchText ->
                reset()
                paginator.onLoadNext {
                    getInventories(
                        searchQuery = searchText,
                        params = getState().params,
                        offset = it
                    )
                }
            }
        }

        override suspend fun executeIntent(
            intent: InventoriesStore.Intent,
            getState: () -> InventoriesStore.State
        ) {
            when (intent) {
                InventoriesStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                InventoriesStore.Intent.OnFilterClicked -> publish(
                    InventoriesStore.Label.ShowFilter(
                        getState().params ?: inventoriesInteractor.getFilters()
                    )
                )
                is InventoriesStore.Intent.OnFilterResult -> {
                    dispatch(Result.FilterParams(intent.params))
                    reset()
                    paginator.onLoadNext {
                        getInventories(
                            params = getState().params,
                            searchQuery = getState().searchText,
                            offset = it
                        )
                    }
                }
                is InventoriesStore.Intent.OnInventoryClicked -> publish(
                    InventoriesStore.Label.ShowInventoryDetail(
                        intent.inventory,
                        intent.inventory.inventoryStatus.toInventoryContainerType()
                    )
                )
                InventoriesStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is InventoriesStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
                is InventoriesStore.Intent.OnLoadNext -> paginator.onLoadNext {
                    getInventories(
                        params = getState().params,
                        searchQuery = getState().searchText,
                        offset = it
                    )
                }
                is InventoriesStore.Intent.OnInventoryResult -> {
                    if (intent.changed) {
                        reset()
                        paginator.onLoadNext {
                            getInventories(
                                params = getState().params,
                                searchQuery = getState().searchText,
                                offset = it
                            )
                        }
                    }
                }
            }
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.emit("")
            } else {
                publish(InventoriesStore.Label.GoBack)
            }
        }

        private suspend fun getInventories(
            searchQuery: String = "",
            params: List<ParamDomain>?,
            offset: Long
        ) = runCatching {
            val inventories = inventoriesInteractor.getInventories(
                searchQuery = searchQuery,
                params = params,
                offset = offset,
                limit = Paginator.PAGE_SIZE
            )
            inventories
        }

        private suspend fun reset() {
            paginator.reset()
            dispatch(Result.IsListEndReached(false))
            dispatch(Result.Inventories(listOf()))
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(InventoriesStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Inventories(val inventories: List<InventoryCreateDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class FilterParams(val params: List<ParamDomain>) : Result()
        data class IsListEndReached(val isListEndReached: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<InventoriesStore.State, Result> {
        override fun InventoriesStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Inventories -> copy(inventories = result.inventories)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.FilterParams -> copy(params = result.params)
                is Result.IsListEndReached -> copy(isListEndReached = result.isListEndReached)
            }
    }
}
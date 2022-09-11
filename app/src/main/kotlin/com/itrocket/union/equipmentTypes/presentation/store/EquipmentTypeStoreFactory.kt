package com.itrocket.union.equipmentTypes.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.equipmentTypes.domain.EquipmentTypeInteractor
import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.paging.Paginator
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class EquipmentTypeStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val typesInteractor: EquipmentTypeInteractor,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {
    fun create(): EquipmentTypeStore =
        object : EquipmentTypeStore,
            Store<EquipmentTypeStore.Intent, EquipmentTypeStore.State, EquipmentTypeStore.Label> by storeFactory.create(
                name = "EquipmentTypesStore",
                initialState = EquipmentTypeStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<EquipmentTypeStore.Intent, Unit, EquipmentTypeStore.State, Result, EquipmentTypeStore.Label> =
        EquipmentTypeExecutor()

    private inner class EquipmentTypeExecutor :
        BaseExecutor<EquipmentTypeStore.Intent, Unit, EquipmentTypeStore.State, Result, EquipmentTypeStore.Label>(
            context = coreDispatchers.ui
        ) {

        private val paginator = Paginator<EquipmentTypesDomain>(
            onError = {
                handleError(it)
            },
            onLoadUpdate = {
                dispatch(Result.Loading(it))
            },
            onSuccess = {
                dispatch(Result.Types(it))
            }
        )

        override suspend fun executeAction(
            action: Unit,
            getState: () -> EquipmentTypeStore.State
        ) {
            searchManager.listenSearch {
                reset()
                paginator.onLoadNext {
                    getEquipments(getState().searchText, offset = it)
                }
            }
        }

        override suspend fun executeIntent(
            intent: EquipmentTypeStore.Intent,
            getState: () -> EquipmentTypeStore.State
        ) {
            when (intent) {
                EquipmentTypeStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                is EquipmentTypeStore.Intent.OnItemClicked -> {
                    publish(EquipmentTypeStore.Label.ShowDetail(intent.id))
                }
                EquipmentTypeStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is EquipmentTypeStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
                is EquipmentTypeStore.Intent.OnLoadNext -> paginator.onLoadNext {
                    getEquipments(
                        searchText = getState().searchText,
                        offset = it
                    )
                }
            }
        }

        private suspend fun getEquipments(searchText: String = "", offset: Long = 0) =
            runCatching {
                val types = typesInteractor.getEquipmentTypes(
                    searchQuery = searchText,
                    offset = offset,
                    limit = Paginator.PAGE_SIZE
                )
                if (types.isEmpty()) {
                    dispatch(Result.IsListEndReached(true))
                }
                types
            }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.emit("")
            } else {
                publish(EquipmentTypeStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(EquipmentTypeStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }

        private suspend fun reset() {
            paginator.reset()
            dispatch(Result.IsListEndReached(false))
            dispatch(Result.Types(listOf()))
        }
    }

    private sealed class Result {
        data class Types(val types: List<EquipmentTypesDomain>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class IsListEndReached(val isListEndReached: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<EquipmentTypeStore.State, Result> {
        override fun EquipmentTypeStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Types -> copy(types = result.types)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.IsListEndReached -> copy(isListEndReached = result.isListEndReached)
            }
    }
}
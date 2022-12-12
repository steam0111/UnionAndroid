package com.itrocket.union.labelType.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.labelType.domain.LabelTypeInteractor
import com.itrocket.union.labelType.domain.entity.LabelTypeDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.equipmentTypes.presentation.store.EquipmentTypeStore
import com.itrocket.union.equipmentTypes.presentation.store.EquipmentTypeStoreFactory
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.paging.Paginator

class LabelTypeStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val labelTypeInteractor: LabelTypeInteractor,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager,
    private val arguments: LabelTypeArguments
) {
    fun create(): LabelTypeStore =
        object : LabelTypeStore,
            Store<LabelTypeStore.Intent, LabelTypeStore.State, LabelTypeStore.Label> by storeFactory.create(
                name = "LabelTypeStore",
                initialState = LabelTypeStore.State(isSelectMode = arguments.isSelectMode),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<LabelTypeStore.Intent, Unit, LabelTypeStore.State, Result, LabelTypeStore.Label> =
        LabelTypeExecutor()

    private inner class LabelTypeExecutor :
        BaseExecutor<LabelTypeStore.Intent, Unit, LabelTypeStore.State, Result, LabelTypeStore.Label>(
            context = coreDispatchers.ui
        ) {

        private val paginator = Paginator<LabelTypeDomain>(
            onError = {
                handleError(it)
            },
            onLoadUpdate = {
                dispatch(Result.Loading(it))
            },
            onSuccess = {
                dispatch(Result.Types(it))
            },
            onEndReached = {
                dispatch(Result.IsListEndReached(true))
            }
        )

        override suspend fun executeAction(
            action: Unit,
            getState: () -> LabelTypeStore.State
        ) {
            searchManager.listenSearch { searchText ->
                reset()
                paginator.onLoadNext {
                    getEquipments(searchText = searchText, offset = it)
                }
            }
        }

        override suspend fun executeIntent(
            intent: LabelTypeStore.Intent,
            getState: () -> LabelTypeStore.State
        ) {
            when (intent) {
                LabelTypeStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                is LabelTypeStore.Intent.OnItemClicked -> onItemClicked(
                    labelTypeId = intent.id,
                    isSelectMode = getState().isSelectMode
                )
                LabelTypeStore.Intent.OnSearchClicked -> dispatch(
                    Result.IsShowSearch(
                        true
                    )
                )
                is LabelTypeStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }
                is LabelTypeStore.Intent.OnLoadNext -> paginator.onLoadNext {
                    getEquipments(
                        searchText = getState().searchText,
                        offset = it
                    )
                }
            }
        }

        private fun onItemClicked(labelTypeId: String, isSelectMode: Boolean) {
            if (isSelectMode) {
                publish(LabelTypeStore.Label.GoBack(LabelTypeResult(labelTypeId = labelTypeId)))
            } else {
                publish(LabelTypeStore.Label.ShowDetail(labelTypeId))
            }
        }

        private suspend fun getEquipments(searchText: String = "", offset: Long = 0) =
            runCatching {
                val types = labelTypeInteractor.getLabelTypes(
                    searchQuery = searchText,
                    offset = offset,
                    limit = Paginator.PAGE_SIZE
                )
                types
            }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.emit("")
            } else {
                publish(LabelTypeStore.Label.GoBack())
            }
        }

        private suspend fun reset() {
            paginator.reset()
            dispatch(Result.IsListEndReached(false))
            dispatch(Result.Types(listOf()))
        }

        override fun handleError(throwable: Throwable) {
            publish(LabelTypeStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Types(val types: List<LabelTypeDomain>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class IsListEndReached(val isListEndReached: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<LabelTypeStore.State, Result> {
        override fun LabelTypeStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Types -> copy(types = result.types)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.IsListEndReached -> copy(isListEndReached = result.isListEndReached)
            }
    }
}
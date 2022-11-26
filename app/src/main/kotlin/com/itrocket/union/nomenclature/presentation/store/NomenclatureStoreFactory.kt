package com.itrocket.union.nomenclature.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.nomenclature.domain.NomenclatureInteractor
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.paging.Paginator
import org.koin.androidx.compose.get


class NomenclatureStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val nomenclatureInteractor: NomenclatureInteractor,
    private val nomenclatureArguments: NomenclatureArguments,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {

    fun create(): NomenclatureStore =
        object : NomenclatureStore,
            Store<NomenclatureStore.Intent, NomenclatureStore.State, NomenclatureStore.Label> by storeFactory.create(
                name = "NomenclatureStore",
                initialState = NomenclatureStore.State(params = nomenclatureInteractor.getFilters()),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<NomenclatureStore.Intent, Unit, NomenclatureStore.State, Result, NomenclatureStore.Label> =
        NomenclatureExecutor()

    private inner class NomenclatureExecutor :
        BaseExecutor<NomenclatureStore.Intent, Unit, NomenclatureStore.State, Result, NomenclatureStore.Label>(
            context = coreDispatchers.ui
        ) {

        private val paginator = Paginator<NomenclatureDomain>(
            onError = {
                handleError(it)
            },
            onLoadUpdate = {
                dispatch(Result.Loading(it))
            },
            onSuccess = {
                dispatch(Result.Nomenclatures(it))
            },
            onEndReached = {
                dispatch(Result.IsListEndReached(true))
            }
        )

        override suspend fun executeAction(
            action: Unit,
            getState: () -> NomenclatureStore.State
        ) {
            searchManager.listenSearch { searchText ->
                reset()
                paginator.onLoadNext {
                    getNomenclatures(params = getState().params, getState().searchText, offset = it)
                }
            }
        }

        override suspend fun executeIntent(
            intent: NomenclatureStore.Intent,
            getState: () -> NomenclatureStore.State
        ) {
            when (intent) {
                NomenclatureStore.Intent.OnBackClicked -> onBackClicked(
                    getState().isShowSearch
                )

                is NomenclatureStore.Intent.OnItemClicked -> publish(
                    NomenclatureStore.Label.ShowDetail(
                        intent.id
                    )
                )

                is NomenclatureStore.Intent.OnFilterClicked -> publish(
                    NomenclatureStore.Label.ShowFilter(getState().params)
                )

                is NomenclatureStore.Intent.OnFilterResult -> {
                    dispatch(Result.Params(intent.params))
                    reset()
                    paginator.onLoadNext {
                        getNomenclatures(
                            params = getState().params,
                            searchText = getState().searchText,
                            offset = it
                        )
                    }
                }

                NomenclatureStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is NomenclatureStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }

                is NomenclatureStore.Intent.OnLoadNext -> paginator.onLoadNext {
                    getNomenclatures(
                        params = getState().params,
                        searchText = getState().searchText,
                        offset = it
                    )
                }
            }
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.emit("")
            } else {
                publish(NomenclatureStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(NomenclatureStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        private suspend fun getNomenclatures(
            params: List<ParamDomain>? = null,
            searchText: String = "",
            offset: Long = 0
        ) = runCatching {
            val nomenclatures = nomenclatureInteractor.getNomenclatures(
                params = params,
                searchQuery = searchText,
                offset = offset,
                limit = Paginator.PAGE_SIZE
            )
            nomenclatures
        }

        private suspend fun reset() {
            paginator.reset()
            dispatch(Result.IsListEndReached(false))
            dispatch(Result.Nomenclatures(listOf()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Nomenclatures(val nomenclatures: List<NomenclatureDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class IsListEndReached(val isListEndReached: Boolean) : Result()
        data class Params(val params: List<ParamDomain>) : Result()
    }

    private object ReducerImpl : Reducer<NomenclatureStore.State, Result> {
        override fun NomenclatureStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Nomenclatures -> copy(nomenclatures = result.nomenclatures)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.IsListEndReached -> copy(isListEndReached = result.isListEndReached)
                is Result.Params -> copy(params = result.params)
            }
    }
}
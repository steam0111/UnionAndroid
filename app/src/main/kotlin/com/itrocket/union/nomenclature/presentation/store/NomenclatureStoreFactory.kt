package com.itrocket.union.nomenclature.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.nomenclature.domain.NomenclatureInteractor
import com.itrocket.union.nomenclature.domain.entity.NomenclatureDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull

class NomenclatureStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val nomenclatureInteractor: NomenclatureInteractor,
    private val nomenclatureArguments: NomenclatureArguments,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {
    private var params: List<ParamDomain>? = null

    fun create(): NomenclatureStore =
        object : NomenclatureStore,
            Store<NomenclatureStore.Intent, NomenclatureStore.State, NomenclatureStore.Label> by storeFactory.create(
                name = "NomenclatureStore",
                initialState = NomenclatureStore.State(),
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
        override suspend fun executeAction(
            action: Unit,
            getState: () -> NomenclatureStore.State
        ) {
            searchManager.listenSearch {
                getNomenclatures(searchText = it, params = params)
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
                    NomenclatureStore.Label.ShowFilter(
                        params ?: nomenclatureInteractor.getFilters()
                    )
                )
                is NomenclatureStore.Intent.OnFilterResult -> {
                    params = intent.params
                    getNomenclatures(params, getState().searchText)
                }
                NomenclatureStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is NomenclatureStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.searchQuery.emit(intent.searchText)
                }
            }
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                searchManager.searchQuery.emit("")
            } else {
                publish(NomenclatureStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(NomenclatureStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }

        private suspend fun getNomenclatures(
            params: List<ParamDomain>? = null,
            searchText: String = ""
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.Nomenclatures(
                        nomenclatureInteractor.getNomenclatures(
                            params,
                            searchText
                        )
                    )
                )
                dispatch(Result.Loading(false))
            }
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Nomenclatures(val nomenclatures: List<NomenclatureDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<NomenclatureStore.State, Result> {
        override fun NomenclatureStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Nomenclatures -> copy(nomenclatures = result.nomenclatures)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
            }
    }
}
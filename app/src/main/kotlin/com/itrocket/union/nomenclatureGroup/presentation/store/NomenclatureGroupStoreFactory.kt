package com.itrocket.union.nomenclatureGroup.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.nomenclatureGroup.domain.NomenclatureGroupInteractor
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.union.utils.ifBlankOrNull

class NomenclatureGroupStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val nomenclatureGroupInteractor: NomenclatureGroupInteractor,
    private val nomenclatureGroupArguments: NomenclatureGroupArguments,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {
    fun create(): NomenclatureGroupStore =
        object : NomenclatureGroupStore,
            Store<NomenclatureGroupStore.Intent, NomenclatureGroupStore.State, NomenclatureGroupStore.Label> by storeFactory.create(
                name = "NomenclatureGroupStore",
                initialState = NomenclatureGroupStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<NomenclatureGroupStore.Intent, Unit, NomenclatureGroupStore.State, Result, NomenclatureGroupStore.Label> =
        NomenclatureGroupExecutor()

    private inner class NomenclatureGroupExecutor :
        BaseExecutor<NomenclatureGroupStore.Intent, Unit, NomenclatureGroupStore.State, Result, NomenclatureGroupStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> NomenclatureGroupStore.State
        ) {
            searchManager.listenSearch {
                getNomenclatureGroup(searchText = it)
            }
        }

        override suspend fun executeIntent(
            intent: NomenclatureGroupStore.Intent,
            getState: () -> NomenclatureGroupStore.State
        ) {
            when (intent) {
                is NomenclatureGroupStore.Intent.OnItemClick -> publish(
                    NomenclatureGroupStore.Label.ShowDetail(
                        intent.id
                    )
                )
                NomenclatureGroupStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                NomenclatureGroupStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is NomenclatureGroupStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.searchQuery.emit(intent.searchText)
                }
            }
        }

        private suspend fun getNomenclatureGroup(searchText: String = "") {
            dispatch(Result.Loading(true))
            catchException {
                dispatch(
                    Result.NomenclatureGroups(
                        nomenclatureGroupInteractor.getNomenclatureGroups(
                            searchQuery = searchText
                        )
                    )
                )
            }
            dispatch(Result.Loading(false))
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.searchQuery.emit("")
            } else {
                publish(NomenclatureGroupStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(NomenclatureGroupStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class NomenclatureGroups(val nomenclatureGroupsDomain: List<NomenclatureGroupDomain>) :
            Result()

        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<NomenclatureGroupStore.State, Result> {
        override fun NomenclatureGroupStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.NomenclatureGroups -> copy(nomenclatureGroups = result.nomenclatureGroupsDomain)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
            }
    }
}
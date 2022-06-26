package com.itrocket.union.departments.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.departments.domain.DepartmentInteractor
import com.itrocket.union.departments.domain.entity.DepartmentDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.search.SearchManager

class DepartmentStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val departmentInteractor: DepartmentInteractor,
    private val searchManager: SearchManager
) {

    private var params: List<ParamDomain>? = null

    fun create(): DepartmentStore =
        object : DepartmentStore,
            Store<DepartmentStore.Intent, DepartmentStore.State, DepartmentStore.Label> by storeFactory.create(
                name = "DepartmentStore",
                initialState = DepartmentStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<DepartmentStore.Intent, Unit, DepartmentStore.State, Result, DepartmentStore.Label> =
        DepartmentExecutor()

    private inner class DepartmentExecutor :
        BaseExecutor<DepartmentStore.Intent, Unit, DepartmentStore.State, Result, DepartmentStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> DepartmentStore.State
        ) {
            searchManager.listenSearch {
                getDepartments(params = params, searchText = it)
            }
        }

        override suspend fun executeIntent(
            intent: DepartmentStore.Intent,
            getState: () -> DepartmentStore.State
        ) {
            when (intent) {
                DepartmentStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                DepartmentStore.Intent.OnFilterClicked -> {
                    publish(
                        DepartmentStore.Label.ShowFilter(
                            params ?: departmentInteractor.getFilters()
                        )
                    )
                }
                DepartmentStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is DepartmentStore.Intent.OnDepartmentClicked -> {
                    publish(DepartmentStore.Label.ShowDetail(intent.id))
                }
                is DepartmentStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.searchQuery.emit(intent.searchText)
                }
                is DepartmentStore.Intent.OnFilterResult -> {
                    params = intent.params
                    getDepartments(getState().searchText, params)
                }
            }
        }

        private suspend fun getDepartments(
            searchText: String = "",
            params: List<ParamDomain>? = null
        ) {
            dispatch(Result.Loading(true))
            catchException {
                dispatch(
                    Result.Departments(
                        departmentInteractor.getDepartments(
                            params,
                            searchText
                        )
                    )
                )
            }
            dispatch(Result.Loading(false))
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                searchManager.searchQuery.emit("")
            } else {
                publish(DepartmentStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(DepartmentStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Departments(val items: List<DepartmentDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<DepartmentStore.State, Result> {
        override fun DepartmentStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Departments -> copy(departments = result.items)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
            }
    }
}
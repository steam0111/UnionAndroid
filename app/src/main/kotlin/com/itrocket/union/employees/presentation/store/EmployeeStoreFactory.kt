package com.itrocket.union.employees.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.employees.domain.EmployeeInteractor
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.search.SearchManager
import com.itrocket.utils.paging.Paginator

class EmployeeStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val employeeInteractor: EmployeeInteractor,
    private val errorInteractor: ErrorInteractor,
    private val searchManager: SearchManager
) {
    fun create(): EmployeeStore =
        object : EmployeeStore,
            Store<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label> by storeFactory.create(
                name = "EmployeesStore",
                initialState = EmployeeStore.State(params = employeeInteractor.getFilters()),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<EmployeeStore.Intent, Unit, EmployeeStore.State, Result, EmployeeStore.Label> =
        EmployeesExecutor()

    private inner class EmployeesExecutor :
        BaseExecutor<EmployeeStore.Intent, Unit, EmployeeStore.State, Result, EmployeeStore.Label>(
            context = coreDispatchers.ui
        ) {

        private val paginator = Paginator<EmployeeDomain>(
            onError = {
                handleError(it)
            },
            onLoadUpdate = {
                dispatch(Result.Loading(it))
            },
            onSuccess = {
                dispatch(Result.Employees(it))
            },
            onEndReached = {
                dispatch(Result.IsListEndReached(true))
            }
        )

        override suspend fun executeAction(
            action: Unit,
            getState: () -> EmployeeStore.State
        ) {
            searchManager.listenSearch { searchText ->
                reset()
                paginator.onLoadNext {
                    getEmployees(params = getState().params, searchText = searchText, offset = it)
                }
            }
        }

        override suspend fun executeIntent(
            intent: EmployeeStore.Intent,
            getState: () -> EmployeeStore.State
        ) {
            when (intent) {
                EmployeeStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch)
                EmployeeStore.Intent.OnFilterClicked -> publish(
                    EmployeeStore.Label.ShowFilter(getState().params)
                )

                EmployeeStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is EmployeeStore.Intent.OnEmployeeClicked -> {
                    publish(EmployeeStore.Label.ShowDetail(intent.employeeId))
                }

                is EmployeeStore.Intent.OnFilterResult -> {
                    dispatch(Result.Params(intent.params))
                    reset()
                    paginator.onLoadNext {
                        getEmployees(
                            params = getState().params,
                            searchText = getState().searchText,
                            offset = it
                        )
                    }
                }

                is EmployeeStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    searchManager.emit(intent.searchText)
                }

                is EmployeeStore.Intent.OnLoadNext -> paginator.onLoadNext {
                    getEmployees(
                        params = getState().params,
                        searchText = getState().searchText,
                        offset = it
                    )
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(EmployeeStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        private suspend fun onBackClicked(isShowSearch: Boolean) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                searchManager.emit("")
            } else {
                publish(EmployeeStore.Label.GoBack)
            }
        }

        private suspend fun getEmployees(
            params: List<ParamDomain>? = null,
            searchText: String = "",
            offset: Long = 0
        ) = runCatching {
            val employees = employeeInteractor.getEmployees(
                params,
                searchText,
                offset = offset,
                limit = Paginator.PAGE_SIZE
            )

            employees
        }

        private suspend fun reset() {
            paginator.reset()
            dispatch(Result.IsListEndReached(false))
            dispatch(Result.Employees(listOf()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Employees(val employees: List<EmployeeDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
        data class IsListEndReached(val isListEndReached: Boolean) : Result()
        data class Params(val params: List<ParamDomain>) : Result()

    }

    private object ReducerImpl : Reducer<EmployeeStore.State, Result> {
        override fun EmployeeStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Employees -> copy(employees = result.employees)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
                is Result.IsListEndReached -> copy(isListEndReached = result.isListEndReached)
                is Result.Params -> copy(params = result.params)
            }
    }
}
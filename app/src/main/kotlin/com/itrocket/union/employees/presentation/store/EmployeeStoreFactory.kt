package com.itrocket.union.employees.presentation.store

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.employees.domain.EmployeeInteractor
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.utils.ifBlankOrNull

class EmployeeStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val employeeInteractor: EmployeeInteractor,
    private val errorInteractor: ErrorInteractor
) {

    private var params: List<ParamDomain>? = null

    fun create(): EmployeeStore =
        object : EmployeeStore,
            Store<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label> by storeFactory.create(
                name = "EmployeesStore",
                initialState = EmployeeStore.State(),
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
        override suspend fun executeAction(
            action: Unit,
            getState: () -> EmployeeStore.State
        ) {
            getEmployees()
        }

        override suspend fun executeIntent(
            intent: EmployeeStore.Intent,
            getState: () -> EmployeeStore.State
        ) {
            when (intent) {
                EmployeeStore.Intent.OnBackClicked -> onBackClicked(getState().isShowSearch, params)
                EmployeeStore.Intent.OnFilterClicked -> publish(
                    EmployeeStore.Label.ShowFilter(
                        params ?: employeeInteractor.getFilters()
                    )
                )
                EmployeeStore.Intent.OnSearchClicked -> dispatch(Result.IsShowSearch(true))
                is EmployeeStore.Intent.OnEmployeeClicked -> {
                    publish(EmployeeStore.Label.ShowDetail(intent.employeeId))
                }
                is EmployeeStore.Intent.OnFilterResult -> {
                    params = intent.params
                    getEmployees(params, getState().searchText)
                }
                is EmployeeStore.Intent.OnSearchTextChanged -> {
                    dispatch(Result.SearchText(intent.searchText))
                    getEmployees(params, intent.searchText)
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(EmployeeStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }

        private suspend fun onBackClicked(isShowSearch: Boolean, params: List<ParamDomain>?) {
            if (isShowSearch) {
                dispatch(Result.IsShowSearch(false))
                dispatch(Result.SearchText(""))
                getEmployees(params, "")
            } else {
                publish(EmployeeStore.Label.GoBack)
            }
        }

        private suspend fun getEmployees(
            params: List<ParamDomain>? = null,
            searchText: String = ""
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(Result.Employees(employeeInteractor.getEmployees(params, searchText)))
                dispatch(Result.Loading(false))
            }
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Employees(val employees: List<EmployeeDomain>) : Result()
        data class SearchText(val searchText: String) : Result()
        data class IsShowSearch(val isShowSearch: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<EmployeeStore.State, Result> {
        override fun EmployeeStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Employees -> copy(employees = result.employees)
                is Result.IsShowSearch -> copy(isShowSearch = result.isShowSearch)
                is Result.SearchText -> copy(searchText = result.searchText)
            }
    }
}
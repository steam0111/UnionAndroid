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

class EmployeeStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val employeeInteractor: EmployeeInteractor,
) {
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
            dispatch(Result.Loading(true))
            catchException {
                dispatch(Result.Employees(employeeInteractor.getEmployees()))
            }
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: EmployeeStore.Intent,
            getState: () -> EmployeeStore.State
        ) {
            when (intent) {
                EmployeeStore.Intent.OnBackClicked -> publish(EmployeeStore.Label.GoBack)
                EmployeeStore.Intent.OnFilterClicked -> {}
                EmployeeStore.Intent.OnSearchClicked -> {}
                is EmployeeStore.Intent.OnEmployeeClicked -> {}
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(EmployeeStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Employees(val employees: List<EmployeeDomain>) : Result()
    }

    private object ReducerImpl : Reducer<EmployeeStore.State, Result> {
        override fun EmployeeStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Employees -> copy(employees = result.employees)
            }
    }
}
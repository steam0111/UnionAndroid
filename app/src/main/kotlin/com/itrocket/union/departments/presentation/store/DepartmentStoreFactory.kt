package com.itrocket.union.departments.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.departments.domain.DepartmentInteractor
import com.itrocket.union.departments.domain.entity.DepartmentDomain

class DepartmentStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val departmentInteractor: DepartmentInteractor,
) {
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
            dispatch(Result.Loading(true))
            catchException {
                dispatch(Result.Employees(departmentInteractor.getDepartments()))
            }
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: DepartmentStore.Intent,
            getState: () -> DepartmentStore.State
        ) {
            when (intent) {
                DepartmentStore.Intent.OnBackClicked -> publish(DepartmentStore.Label.GoBack)
                DepartmentStore.Intent.OnFilterClicked -> {}
                DepartmentStore.Intent.OnSearchClicked -> {}
                is DepartmentStore.Intent.OnDepartmentClicked -> {}
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(DepartmentStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Employees(val employees: List<DepartmentDomain>) : Result()
    }

    private object ReducerImpl : Reducer<DepartmentStore.State, Result> {
        override fun DepartmentStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Employees -> copy(departments = result.employees)
            }
    }
}
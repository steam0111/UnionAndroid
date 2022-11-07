package com.itrocket.union.employeeDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain
import com.itrocket.union.employeeDetail.domain.EmployeeDetailInteractor
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.utils.ifBlankOrNull

class EmployeeDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: EmployeeDetailInteractor,
    private val args: EmployeeDetailArguments,
    private val errorInteractor: ErrorInteractor
) {

    fun create(): EmployeeDetailStore =
        object : EmployeeDetailStore,
            Store<EmployeeDetailStore.Intent, EmployeeDetailStore.State, EmployeeDetailStore.Label> by storeFactory.create(
                name = "EmployeeDetailStore",
                initialState = EmployeeDetailStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<EmployeeDetailStore.Intent, Unit, EmployeeDetailStore.State, Result, EmployeeDetailStore.Label> =
        EmployeeDetailExecutor()

    private inner class EmployeeDetailExecutor :
        BaseExecutor<EmployeeDetailStore.Intent, Unit, EmployeeDetailStore.State, Result, EmployeeDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> EmployeeDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                interactor.getEmployeeDetail(args.employeeId)?.let {
                    dispatch(Result.EmployeeDetail(it))
                }
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(EmployeeDetailStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }

        override suspend fun executeIntent(
            intent: EmployeeDetailStore.Intent,
            getState: () -> EmployeeDetailStore.State
        ) {
            when (intent) {
                EmployeeDetailStore.Intent.OnBackClicked -> publish(
                    EmployeeDetailStore.Label.GoBack
                )
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class EmployeeDetail(val employee: EmployeeDetailDomain) : Result()
    }

    private object ReducerImpl :
        Reducer<EmployeeDetailStore.State, Result> {
        override fun EmployeeDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.EmployeeDetail -> copy(item = result.employee)
            }
    }
}
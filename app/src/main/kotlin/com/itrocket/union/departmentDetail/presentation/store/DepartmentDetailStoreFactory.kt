package com.itrocket.union.departmentDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.departmentDetail.domain.DepartmentDetailInteractor
import com.itrocket.union.departmentDetail.domain.entity.DepartmentDetailDomain

class DepartmentDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: DepartmentDetailInteractor,
    private val args: DepartmentDetailArguments
) {

    fun create(): DepartmentDetailStore =
        object : DepartmentDetailStore,
            Store<DepartmentDetailStore.Intent, DepartmentDetailStore.State, DepartmentDetailStore.Label> by storeFactory.create(
                name = "DepartmentDetailStore",
                initialState = DepartmentDetailStore.State(
                    item = DepartmentDetailDomain(emptyList())
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<DepartmentDetailStore.Intent, Unit, DepartmentDetailStore.State, Result, DepartmentDetailStore.Label> =
        DepartmentDetailExecutor()

    private inner class DepartmentDetailExecutor :
        BaseExecutor<DepartmentDetailStore.Intent, Unit, DepartmentDetailStore.State, Result, DepartmentDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> DepartmentDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.Department(
                        interactor.getDepartmentDetail(args.id)
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(DepartmentDetailStore.Label.Error(throwable.message.orEmpty()))
        }

        override suspend fun executeIntent(
            intent: DepartmentDetailStore.Intent,
            getState: () -> DepartmentDetailStore.State
        ) {
            when (intent) {
                DepartmentDetailStore.Intent.OnBackClicked -> publish(
                    DepartmentDetailStore.Label.GoBack
                )
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class Department(val item: DepartmentDetailDomain) : Result()
    }

    private object ReducerImpl :
        Reducer<DepartmentDetailStore.State, Result> {
        override fun DepartmentDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Department -> copy(item = result.item)
            }
    }
}
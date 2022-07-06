package com.itrocket.union.branchDetail.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.branchDetail.domain.BranchDetailInteractor
import com.itrocket.union.branchDetail.domain.entity.BranchDetailDomain


class BranchDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val interactor: BranchDetailInteractor,
    private val args: BranchDetailArguments
) {

    fun create(): BranchDetailStore =
        object : BranchDetailStore,
            Store<BranchDetailStore.Intent, BranchDetailStore.State, BranchDetailStore.Label> by storeFactory.create(
                name = "BranchDetailStore",
                initialState = BranchDetailStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<BranchDetailStore.Intent, Unit, BranchDetailStore.State, Result, BranchDetailStore.Label> =
        BranchDetailExecutor()

    private inner class BranchDetailExecutor :
        BaseExecutor<BranchDetailStore.Intent, Unit, BranchDetailStore.State, Result, BranchDetailStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> BranchDetailStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                dispatch(
                    Result.BranchDetail(
                        interactor.getBranchDetail(args.id)
                    )
                )
                dispatch(Result.Loading(false))
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(BranchDetailStore.Label.Error(throwable.message.orEmpty()))
        }

        override suspend fun executeIntent(
            intent: BranchDetailStore.Intent,
            getState: () -> BranchDetailStore.State
        ) {
            when (intent) {
                BranchDetailStore.Intent.OnBackClicked -> publish(BranchDetailStore.Label.GoBack)
            }
        }
    }

    private sealed class Result {
        class Loading(val isLoading: Boolean) : Result()
        class BranchDetail(val item: BranchDetailDomain) : Result()
    }

    private object ReducerImpl :
        Reducer<BranchDetailStore.State, Result> {
        override fun BranchDetailStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.BranchDetail -> copy(item = result.item)
            }
    }
}
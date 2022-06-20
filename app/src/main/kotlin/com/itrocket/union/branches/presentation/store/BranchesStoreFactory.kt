package com.itrocket.union.branches.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.branches.domain.BranchesInteractor
import com.itrocket.union.branches.domain.entity.BranchesDomain
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.utils.ifBlankOrNull
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class BranchesStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val branchesInteractor: BranchesInteractor,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): BranchesStore =
        object : BranchesStore,
            Store<BranchesStore.Intent, BranchesStore.State, BranchesStore.Label> by storeFactory.create(
                name = "BranchesStore",
                initialState = BranchesStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<BranchesStore.Intent, Unit, BranchesStore.State, Result, BranchesStore.Label> =
        BranchesExecutor()

    private inner class BranchesExecutor :
        BaseExecutor<BranchesStore.Intent, Unit, BranchesStore.State, Result, BranchesStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> BranchesStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                branchesInteractor.getBranches()
                    .catch { handleError(it) }
                    .collect {
                        dispatch(Result.Branches(it))
                        dispatch(Result.Loading(false))
                    }
            }
        }

        override suspend fun executeIntent(
            intent: BranchesStore.Intent,
            getState: () -> BranchesStore.State
        ) {
            when (intent) {
                BranchesStore.Intent.OnBackClicked -> publish(BranchesStore.Label.GoBack)
                is BranchesStore.Intent.OnBranchClicked -> {
                }
                BranchesStore.Intent.OnFilterClicked -> {
                }
                BranchesStore.Intent.OnSearchClicked -> {
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            dispatch(Result.Loading(false))
            publish(BranchesStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
        }
    }

    private sealed class Result {
        data class Branches(val branches: List<BranchesDomain>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<BranchesStore.State, Result> {
        override fun BranchesStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Branches -> copy(branches = result.branches)
            }
    }
}
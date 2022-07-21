package com.itrocket.union.syncAll.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.syncAll.domain.SyncAllInteractor

class SyncAllStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val syncAllInteractor: SyncAllInteractor,
    private val errorInteractor: ErrorInteractor,
) {
    fun create(): SyncAllStore =
        object : SyncAllStore,
            Store<SyncAllStore.Intent, SyncAllStore.State, SyncAllStore.Label> by storeFactory.create(
                name = "SyncAllStore",
                initialState = SyncAllStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<SyncAllStore.Intent, Unit, SyncAllStore.State, Result, SyncAllStore.Label> =
        SyncAllExecutor()

    private inner class SyncAllExecutor :
        BaseExecutor<SyncAllStore.Intent, Unit, SyncAllStore.State, Result, SyncAllStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> SyncAllStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: SyncAllStore.Intent,
            getState: () -> SyncAllStore.State
        ) {
            when (intent) {
                SyncAllStore.Intent.OnBackClicked -> publish(SyncAllStore.Label.GoBack)
                SyncAllStore.Intent.OnSyncButtonClicked -> {
                    dispatch(Result.Loading(true))
                    catchException {
                        syncAllInteractor.syncAll()
                        publish(SyncAllStore.Label.ShowMenu)
                    }
                    dispatch(Result.Loading(false))
                }
                SyncAllStore.Intent.OnClearButtonClicked -> {
                    catchException {
                        syncAllInteractor.clearAll()
                        publish(SyncAllStore.Label.ShowMenu)
                    }
                }
                SyncAllStore.Intent.OnAuthButtonClicked -> {
                    publish(SyncAllStore.Label.ShowAuth)
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(SyncAllStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<SyncAllStore.State, Result> {
        override fun SyncAllStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
            }
    }
}
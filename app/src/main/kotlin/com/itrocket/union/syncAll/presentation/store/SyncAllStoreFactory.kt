package com.itrocket.union.syncAll.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.union_sync_api.entity.SyncEvent
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.syncAll.domain.SyncAllInteractor
import com.itrocket.union.syncAll.presentation.store.SyncAllStoreFactory.Result.ClearSyncEvents
import com.itrocket.union.syncAll.presentation.store.SyncAllStoreFactory.Result.Loading
import com.itrocket.union.syncAll.presentation.store.SyncAllStoreFactory.Result.NewSyncEvent
import kotlinx.coroutines.flow.collect

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
            syncAllInteractor
                .subscribeSyncEvents()
                .collect {
                    dispatch(NewSyncEvent(it))
                }
        }

        override suspend fun executeIntent(
            intent: SyncAllStore.Intent,
            getState: () -> SyncAllStore.State
        ) {
            when (intent) {
                SyncAllStore.Intent.OnBackClicked -> publish(SyncAllStore.Label.GoBack)
                SyncAllStore.Intent.OnSyncButtonClicked -> {
                    dispatch(Loading(true))
                    dispatch(ClearSyncEvents)
                    catchException {
                        syncAllInteractor.syncAll()
                    }
                    dispatch(Loading(false))
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
        data class NewSyncEvent(val newSyncEvent: SyncEvent) : Result()
        object ClearSyncEvents : Result()
    }

    private object ReducerImpl : Reducer<SyncAllStore.State, Result> {
        override fun SyncAllStore.State.reduce(result: Result) =
            when (result) {
                is Loading -> copy(isLoading = result.isLoading)
                is NewSyncEvent -> copy(syncEvents = syncEvents.plus(result.newSyncEvent))
                ClearSyncEvents -> copy(syncEvents = emptyList())
            }
    }
}
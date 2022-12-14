package com.itrocket.union.syncAll.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_api.entity.SyncInfoType
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.syncAll.domain.SyncAllInteractor
import com.itrocket.union.syncAll.presentation.store.SyncAllStoreFactory.Result.Loading
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SyncAllStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val arguments: SyncAllArguments,
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
            coroutineScope {
                launch {
                    dispatch(Result.LastDateSync(syncAllInteractor.getLastSyncDate()))
                }
                launch {
                    syncAllInteractor
                        .subscribeSyncEvents()
                        .collect {
                            dispatch(
                                Result.NewSyncEvents(
                                    syncAllInteractor.addNewEvent(
                                        getState().syncEvents,
                                        it
                                    )
                                )
                            )
                        }
                }
                launch {
                    syncAllInteractor
                        .subscribeSyncInfo()
                        .collect {
                            collectSyncInfoType(
                                syncInfoType = it,
                                syncedCount = getState().syncedCount
                            )
                        }
                }
                launch {
                    if (arguments.isInstantSync) {
                        syncAll(getState)
                    }
                }
            }
        }

        override suspend fun executeIntent(
            intent: SyncAllStore.Intent,
            getState: () -> SyncAllStore.State
        ) {
            when (intent) {
                SyncAllStore.Intent.OnBackClicked -> publish(SyncAllStore.Label.ShowMenu)
                SyncAllStore.Intent.OnSyncButtonClicked -> syncAll(getState)
                SyncAllStore.Intent.OnChangeLogVisibilityClicked -> onChangeLogVisibilityClicked(
                    getState().isShowLog
                )
                SyncAllStore.Intent.OnFinishClicked -> onFinishClicked()
            }
        }

        private fun collectSyncInfoType(syncInfoType: SyncInfoType, syncedCount: Long) {
            when (syncInfoType) {
                is SyncInfoType.ItemCount -> {
                    dispatchCount(itemCount = syncInfoType, syncedCount = syncedCount)
                }
                else -> dispatch(
                    Result.SyncTitle(
                        syncAllInteractor.getSyncTitle(
                            syncInfoType
                        )
                    )
                )
            }
        }

        private fun dispatchCount(itemCount: SyncInfoType.ItemCount, syncedCount: Long) {
            if (itemCount.isAllCount) {
                dispatch(Result.AllCount(itemCount.count ?: 0))
            } else {
                dispatch(
                    Result.SyncedCount(
                        syncAllInteractor.getItemCount(
                            itemCount = itemCount,
                            syncedCount = syncedCount
                        )
                    )
                )
            }
        }

        private fun onChangeLogVisibilityClicked(isShowLog: Boolean) {
            dispatch(Result.IsShowLog(!isShowLog))
        }

        private fun onFinishClicked() {
            publish(SyncAllStore.Label.ShowMenu)
        }

        private suspend fun syncAll(getState: () -> SyncAllStore.State) {
            dispatch(Loading(true))
            catchException {
                syncAllInteractor.syncAll(listOf())

                val isSyncSuccess = !syncAllInteractor.isSyncEventHasError(getState().syncEvents)
                dispatch(Result.IsSyncSuccess(isSyncSuccess))
                dispatch(Result.LastDateSync(syncAllInteractor.getLastSyncDate()))
            }
            dispatch(Loading(false))
            dispatch(Result.IsSyncFinished(true))
        }

        override fun handleError(throwable: Throwable) {
            publish(SyncAllStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class NewSyncEvents(val newSyncEvents: List<SyncEvent>) : Result()
        data class IsShowLog(val isShowLog: Boolean) : Result()
        data class IsSyncFinished(val isSyncFinished: Boolean) : Result()
        data class SyncTitle(val title: String) : Result()
        data class AllCount(val count: Long) : Result()
        data class SyncedCount(val count: Long) : Result()
        data class LastDateSync(val lastDateSync: String) : Result()
        data class IsSyncSuccess(val isSuccess: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<SyncAllStore.State, Result> {
        override fun SyncAllStore.State.reduce(result: Result) =
            when (result) {
                is Loading -> copy(isLoading = result.isLoading)
                is Result.NewSyncEvents -> copy(syncEvents = result.newSyncEvents)
                is Result.IsShowLog -> copy(isShowLog = result.isShowLog)
                is Result.IsSyncFinished -> copy(isSyncFinished = result.isSyncFinished)
                is Result.AllCount -> copy(allCount = result.count)
                is Result.SyncedCount -> copy(syncedCount = result.count)
                is Result.SyncTitle -> copy(currentSyncTitle = result.title)
                is Result.LastDateSync -> copy(lastDateSync = result.lastDateSync)
                is Result.IsSyncSuccess -> copy(isSyncSuccess = result.isSuccess)
            }
    }
}
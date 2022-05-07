package com.itrocket.union.readingMode.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.readingMode.domain.ReadingModeInteractor
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

class ReadingModeStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val readingModeInteractor: ReadingModeInteractor,
    private val readingModeArguments: ReadingModeArguments
) {
    fun create(): ReadingModeStore =
        object : ReadingModeStore,
            Store<ReadingModeStore.Intent, ReadingModeStore.State, ReadingModeStore.Label> by storeFactory.create(
                name = "ReadingModeStore",
                initialState = ReadingModeStore.State(
                    tabs = listOf(
                        ReadingModeTab.RFID,
                        ReadingModeTab.BARCODE,
                        ReadingModeTab.SN
                    ),
                    selectedTab = readingModeArguments.selectedReadingMode
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ReadingModeStore.Intent, Unit, ReadingModeStore.State, Result, ReadingModeStore.Label> =
        ReadingModeExecutor()

    private inner class ReadingModeExecutor :
        SuspendExecutor<ReadingModeStore.Intent, Unit, ReadingModeStore.State, Result, ReadingModeStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ReadingModeStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: ReadingModeStore.Intent,
            getState: () -> ReadingModeStore.State
        ) {
            when (intent) {
                ReadingModeStore.Intent.OnCameraClicked -> {
                    //no-op
                }
                ReadingModeStore.Intent.OnManualInputClicked -> {
                    //no-op
                }
                is ReadingModeStore.Intent.OnReadingModeSelected -> {
                    dispatch(Result.ReadingModeSelected(intent.readingMode))
                    dispatch(Result.IsManualInputEnabled(intent.readingMode != ReadingModeTab.RFID))
                }
                ReadingModeStore.Intent.OnSettingsClicked -> {
                    //no-op
                }
            }
        }
    }

    private sealed class Result {
        data class ReadingModeSelected(val readingMode: ReadingModeTab) : Result()
        data class IsManualInputEnabled(val isEnabled: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<ReadingModeStore.State, Result> {
        override fun ReadingModeStore.State.reduce(result: Result) =
            when (result) {
                is Result.ReadingModeSelected -> copy(selectedTab = result.readingMode)
                is Result.IsManualInputEnabled -> copy(isManualInputEnabled = result.isEnabled)
            }
    }
}
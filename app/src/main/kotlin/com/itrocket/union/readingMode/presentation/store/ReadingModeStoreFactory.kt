package com.itrocket.union.readingMode.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manualInput.presentation.store.ManualInputType
import com.itrocket.union.readingMode.domain.ReadingModeInteractor
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.readingMode.presentation.view.toReadingMode
import com.itrocket.union.readingMode.presentation.view.toReadingModeTab
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class ReadingModeStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val readingModeInteractor: ReadingModeInteractor,
    private val serviceEntryManager: ServiceEntryManager
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
                    )
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
            dispatch(Result.ReadingModeSelected(serviceEntryManager.currentMode.toReadingModeTab()))
            readingModeInteractor.changeScanMode(getState().selectedTab.toReadingMode())
        }

        override suspend fun executeIntent(
            intent: ReadingModeStore.Intent,
            getState: () -> ReadingModeStore.State
        ) {
            when (intent) {
                ReadingModeStore.Intent.OnRestartClicked -> {
                    readingModeInteractor.restartService()
                }
                ReadingModeStore.Intent.OnManualInputClicked -> publish(
                    ReadingModeStore.Label.ManualInput(
                        when (getState().selectedTab) {
                            ReadingModeTab.SN -> ManualInputType.SN
                            else -> ManualInputType.BARCODE
                        }
                    )
                )
                is ReadingModeStore.Intent.OnReadingModeSelected -> {
                    readingModeInteractor.changeScanMode(intent.readingMode.toReadingMode())
                    dispatch(Result.ReadingModeSelected(intent.readingMode))
                    publish(ReadingModeStore.Label.ResultReadingTab(intent.readingMode))
                }
                ReadingModeStore.Intent.OnSettingsClicked -> publish(ReadingModeStore.Label.ReaderPower)
                is ReadingModeStore.Intent.OnManualInput -> publish(
                    ReadingModeStore.Label.GoBack(
                        ReadingModeResult(
                            readingModeTab = getState().selectedTab,
                            scanData = intent.text
                        )
                    )
                )
            }
        }
    }

    private sealed class Result {
        data class ReadingModeSelected(val readingMode: ReadingModeTab) : Result()
    }

    private object ReducerImpl : Reducer<ReadingModeStore.State, Result> {
        override fun ReadingModeStore.State.reduce(result: Result) =
            when (result) {
                is Result.ReadingModeSelected -> copy(selectedTab = result.readingMode)
            }
    }
}
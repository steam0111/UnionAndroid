package com.itrocket.union.readerPower.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.readerPower.domain.ReaderPowerInteractor
import com.itrocket.union.readerPower.domain.ReaderPowerInteractor.Companion.MIN_READER_POWER
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ReaderPowerStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val readerPowerInteractor: ReaderPowerInteractor,
) {
    fun create(): ReaderPowerStore =
        object : ReaderPowerStore,
            Store<ReaderPowerStore.Intent, ReaderPowerStore.State, ReaderPowerStore.Label> by storeFactory.create(
                name = "ReaderPowerStore",
                initialState = ReaderPowerStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ReaderPowerStore.Intent, Unit, ReaderPowerStore.State, Result, ReaderPowerStore.Label> =
        ReaderPowerExecutor()

    private inner class ReaderPowerExecutor :
        BaseExecutor<ReaderPowerStore.Intent, Unit, ReaderPowerStore.State, Result, ReaderPowerStore.Label>(
            context = coreDispatchers.ui
        ) {

        private var powerJob: Job? = null

        override suspend fun executeAction(
            action: Unit,
            getState: () -> ReaderPowerStore.State
        ) {
            dispatch(Result.ReaderPower(readerPowerInteractor.getReaderPower()))
        }

        override suspend fun executeIntent(
            intent: ReaderPowerStore.Intent,
            getState: () -> ReaderPowerStore.State
        ) {
            when (intent) {
                ReaderPowerStore.Intent.OnCrossClicked -> publish(ReaderPowerStore.Label.GoBack())
                ReaderPowerStore.Intent.OnAcceptClicked -> {
                    readerPowerInteractor.savePower(getState().readerPower)
                    publish(
                        ReaderPowerStore.Label.GoBack(
                            ReaderPowerResult(getState().readerPower ?: MIN_READER_POWER)
                        )
                    )
                }
                ReaderPowerStore.Intent.OnCancelClicked -> dispatch(
                    Result.ReaderPower(MIN_READER_POWER)
                )
                is ReaderPowerStore.Intent.OnPowerChanged -> onPowerChanged(newPowerText = intent.newPowerText)
                ReaderPowerStore.Intent.OnArrowDownClicked -> {
                    val readerPower = getState().readerPower ?: MIN_READER_POWER
                    val newPower = readerPowerInteractor.increasePower(readerPower)
                    dispatch(Result.ReaderPower(newPower))
                }
                ReaderPowerStore.Intent.OnArrowUpClicked -> {
                    val readerPower = getState().readerPower ?: MIN_READER_POWER
                    val newPower = readerPowerInteractor.decreasePower(readerPower)
                    dispatch(Result.ReaderPower(newPower))
                }
            }
        }

        private suspend fun onPowerChanged(newPowerText: String?) {
            coroutineScope {
                powerJob?.cancel()
                powerJob = launch {
                    val newPower =
                        readerPowerInteractor.changePower(newPowerText?.toIntOrNull())
                    dispatch(Result.ReaderPower(newPower))
                }
            }
        }

        override fun dispose() {
            powerJob?.cancel()
            super.dispose()
        }
    }

    private sealed class Result {
        data class ReaderPower(val newPower: Int?) : Result()
    }

    private object ReducerImpl : Reducer<ReaderPowerStore.State, Result> {
        override fun ReaderPowerStore.State.reduce(result: Result) =
            when (result) {
                is Result.ReaderPower -> copy(readerPower = result.newPower)
            }
    }
}
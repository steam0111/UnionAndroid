package com.itrocket.union.readerView.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.moduleSettings.domain.ModuleSettingsInteractor

class ReaderBottomBarStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val moduleSettingsInteractor: ModuleSettingsInteractor,
) {
    fun create(): ReaderBottomBarStore =
        object : ReaderBottomBarStore,
            Store<Unit, ReaderBottomBarStore.State, Unit> by storeFactory.create(
                name = "ReaderPowerStore",
                initialState = ReaderBottomBarStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<Unit, Unit, ReaderBottomBarStore.State, Result, Unit> =
        ReaderPowerExecutor()

    private inner class ReaderPowerExecutor :
        BaseExecutor<Unit, Unit, ReaderBottomBarStore.State, Result, Unit>(
            context = coreDispatchers.ui
        ) {

        override suspend fun executeAction(
            action: Unit,
            getState: () -> ReaderBottomBarStore.State
        ) {
            moduleSettingsInteractor.getReaderPowerFlow { dispatch(Result.ReaderPower(it)) }
        }
    }

    private sealed class Result {
        data class ReaderPower(val newPower: Int?) : Result()
    }

    private object ReducerImpl : Reducer<ReaderBottomBarStore.State, Result> {
        override fun ReaderBottomBarStore.State.reduce(result: Result) =
            when (result) {
                is Result.ReaderPower -> copy(readerPower = result.newPower)
            }
    }
}
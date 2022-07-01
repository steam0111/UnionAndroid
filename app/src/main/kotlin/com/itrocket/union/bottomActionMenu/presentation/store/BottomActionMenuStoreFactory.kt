package com.itrocket.union.bottomActionMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.core.base.CoreDispatchers

class BottomActionMenuStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val bottomActionMenuArguments: BottomActionMenuArguments
) {
    fun create(): BottomActionMenuStore =
        object : BottomActionMenuStore,
            Store<BottomActionMenuStore.Intent, BottomActionMenuStore.State, BottomActionMenuStore.Label> by storeFactory.create(
                name = "BottomActionMenuStore",
                initialState = BottomActionMenuStore.State(
                    isEnabled = true
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<BottomActionMenuStore.Intent, Unit, BottomActionMenuStore.State, Result, BottomActionMenuStore.Label> =
        BottomActionMenuExecutor()

    private inner class BottomActionMenuExecutor :
        SuspendExecutor<BottomActionMenuStore.Intent, Unit, BottomActionMenuStore.State, Result, BottomActionMenuStore.Label>(
            mainContext = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> BottomActionMenuStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: BottomActionMenuStore.Intent,
            getState: () -> BottomActionMenuStore.State
        ) {
        }

    }

    private sealed class Result {

    }

    private object ReducerImpl : Reducer<BottomActionMenuStore.State, Result> {
        override fun BottomActionMenuStore.State.reduce(result: Result): BottomActionMenuStore.State {
            TODO("Not yet implemented")
        }
    }
}

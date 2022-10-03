package com.itrocket.union.manualInput.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers

class ManualInputStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val arguments: ManualInputArguments
) {
    fun create(): ManualInputStore =
        object : ManualInputStore,
            Store<ManualInputStore.Intent, ManualInputStore.State, ManualInputStore.Label> by storeFactory.create(
                name = "ManualInputStore",
                initialState = ManualInputStore.State(type = arguments.manualType),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ManualInputStore.Intent, Unit, ManualInputStore.State, Result, ManualInputStore.Label> =
        ManualInputExecutor()

    private inner class ManualInputExecutor :
        BaseExecutor<ManualInputStore.Intent, Unit, ManualInputStore.State, Result, ManualInputStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ManualInputStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: ManualInputStore.Intent,
            getState: () -> ManualInputStore.State
        ) {
            when (intent) {
                ManualInputStore.Intent.OnAcceptClicked -> publish(
                    ManualInputStore.Label.GoBack(
                        ManualInputResult(getState().text)
                    )
                )
                is ManualInputStore.Intent.OnTextChanged -> dispatch(Result.Text(intent.text))
                ManualInputStore.Intent.OnCloseClicked -> publish(
                    ManualInputStore.Label.GoBack()
                )
            }
        }

        override fun handleError(throwable: Throwable) {

        }
    }

    private sealed class Result {
        data class Text(val text: String) : Result()
    }

    private object ReducerImpl : Reducer<ManualInputStore.State, Result> {
        override fun ManualInputStore.State.reduce(result: Result) =
            when (result) {
                is Result.Text -> copy(text = result.text)
            }
    }
}
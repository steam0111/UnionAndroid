package com.itrocket.union.switcher.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.switcher.domain.SwitcherInteractor
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.utils.ifBlankOrNull

class SwitcherStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val switcherInteractor: SwitcherInteractor,
    private val switcherArguments: SwitcherArguments,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): SwitcherStore =
        object : SwitcherStore,
            Store<SwitcherStore.Intent, SwitcherStore.State, SwitcherStore.Label> by storeFactory.create(
                name = "SwitcherStore",
                initialState = SwitcherStore.State(
                    switcherData = switcherArguments.argument,
                    selectedPage = switcherArguments.argument.values.indexOf(switcherArguments.argument.currentValue)
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<SwitcherStore.Intent, Unit, SwitcherStore.State, Result, SwitcherStore.Label> =
        SwitcherExecutor()

    private inner class SwitcherExecutor :
        BaseExecutor<SwitcherStore.Intent, Unit, SwitcherStore.State, Result, SwitcherStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> SwitcherStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: SwitcherStore.Intent,
            getState: () -> SwitcherStore.State
        ) {
            when (intent) {
                SwitcherStore.Intent.OnCancelClicked -> publish(SwitcherStore.Label.GoBack(null))
                SwitcherStore.Intent.OnContinueClicked -> {
                    val switcherData = getState().switcherData
                    val selectedPage = getState().selectedPage
                    publish(
                        SwitcherStore.Label.GoBack(
                            SwitcherResult(switcherData.copy(currentValue = switcherData.values[selectedPage]))
                        )
                    )
                }
                SwitcherStore.Intent.OnCrossClicked -> publish(SwitcherStore.Label.GoBack(null))
                is SwitcherStore.Intent.OnTabClicked -> dispatch(Result.Page(intent.page))
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(SwitcherStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private sealed class Result {
        data class Page(val page: Int) : Result()
    }

    private object ReducerImpl : Reducer<SwitcherStore.State, Result> {
        override fun SwitcherStore.State.reduce(result: Result) =
            when (result) {
                is Result.Page -> copy(selectedPage = result.page)
            }
    }
}
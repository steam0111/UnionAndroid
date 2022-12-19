package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor

class SelectActionWithValuesBottomMenuStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val selectActionWithValuesBottomMenuArguments: SelectActionWithValuesBottomMenuArguments,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): SelectActionWithValuesBottomMenuStore =
        object : SelectActionWithValuesBottomMenuStore,
            Store<SelectActionWithValuesBottomMenuStore.Intent, SelectActionWithValuesBottomMenuStore.State, SelectActionWithValuesBottomMenuStore.Label> by storeFactory.create(
                name = "SelectActionWithValuesBottomMenuStore",
                initialState = SelectActionWithValuesBottomMenuStore.State(
                    accountingObject = selectActionWithValuesBottomMenuArguments.accountingObject,
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<SelectActionWithValuesBottomMenuStore.Intent, Unit, SelectActionWithValuesBottomMenuStore.State, Unit, SelectActionWithValuesBottomMenuStore.Label> =
        SelectActionWithValuesBottomMenuExecutor()

    private inner class SelectActionWithValuesBottomMenuExecutor :
        BaseExecutor<SelectActionWithValuesBottomMenuStore.Intent, Unit, SelectActionWithValuesBottomMenuStore.State, Unit, SelectActionWithValuesBottomMenuStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> SelectActionWithValuesBottomMenuStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: SelectActionWithValuesBottomMenuStore.Intent,
            getState: () -> SelectActionWithValuesBottomMenuStore.State
        ) {
            when (intent) {
                is SelectActionWithValuesBottomMenuStore.Intent.OnTypeClicked -> {
                    publish(
                        SelectActionWithValuesBottomMenuStore.Label.GoBack(
                            SelectActionWithValuesBottomMenuResult(
                                actionType = intent.actionsWithIdentifyObjects,
                                accountingObject = getState().accountingObject
                            )
                        )
                    )
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(
                SelectActionWithValuesBottomMenuStore.Label.Error(
                    errorInteractor.getTextMessage(
                        throwable
                    )
                )
            )
        }
    }

    private object ReducerImpl : Reducer<SelectActionWithValuesBottomMenuStore.State, Unit> {
        override fun SelectActionWithValuesBottomMenuStore.State.reduce(result: Unit): SelectActionWithValuesBottomMenuStore.State =
            copy()
    }
}
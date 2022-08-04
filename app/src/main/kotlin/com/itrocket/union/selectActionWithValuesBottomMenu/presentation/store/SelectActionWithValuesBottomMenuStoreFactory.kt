package com.itrocket.union.selectActionWithValuesBottomMenu.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.domain.entity.ActionsWithIdentifyObjects

class SelectActionWithValuesBottomMenuStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val selectActionWithValuesBottomMenuArguments: SelectActionWithValuesBottomMenuArguments
) {
    fun create(): SelectActionWithValuesBottomMenuStore =
        object : SelectActionWithValuesBottomMenuStore,
            Store<SelectActionWithValuesBottomMenuStore.Intent, SelectActionWithValuesBottomMenuStore.State, SelectActionWithValuesBottomMenuStore.Label> by storeFactory.create(
                name = "SelectActionWithValuesBottomMenuStore",
                initialState = SelectActionWithValuesBottomMenuStore.State(
                    actionsWithIdentifyObjects = ActionsWithIdentifyObjects.values().toList(),
                    accountingObjectDomain = selectActionWithValuesBottomMenuArguments.accountingObject,
                    accountingObjects = selectActionWithValuesBottomMenuArguments.accountingObjects
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
                    when (intent.actionsWithIdentifyObjects) {
                        ActionsWithIdentifyObjects.OPEN_CARD -> {
                            publish(SelectActionWithValuesBottomMenuStore.Label.ShowDetail(intent.accountingObjectDomain))
                        }
                        ActionsWithIdentifyObjects.DELETE_FROM_LIST -> {
                            val newList = intent.accountingObjects.toMutableList()
                            newList.removeAt(newList.indexOf(intent.accountingObjectDomain))

                            publish(
                                SelectActionWithValuesBottomMenuStore.Label.DeleteCard(
                                    SelectActionWithValuesBottomMenuResult(
                                        newList
                                    )
                                )
                            )
                        }
                    }
                    publish(
                        SelectActionWithValuesBottomMenuStore.Label.GoBack(
                            SelectActionWithValuesBottomMenuResult()
                        )
                    )
                }
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(SelectActionWithValuesBottomMenuStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private object ReducerImpl : Reducer<SelectActionWithValuesBottomMenuStore.State, Unit> {
        override fun SelectActionWithValuesBottomMenuStore.State.reduce(result: Unit): SelectActionWithValuesBottomMenuStore.State =
            copy()
    }
}
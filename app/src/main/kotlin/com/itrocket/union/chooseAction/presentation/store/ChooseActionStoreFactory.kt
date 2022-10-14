package com.itrocket.union.chooseAction.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.error.ErrorInteractor

class ChooseActionStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val errorInteractor: ErrorInteractor
) {
    fun create(): ChooseActionStore =
        object : ChooseActionStore,
            Store<ChooseActionStore.Intent, ChooseActionStore.State, ChooseActionStore.Label> by storeFactory.create(
                name = "ChooseActionStore",
                initialState = ChooseActionStore.State(
                    types = ObjectType.values().toList()
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<ChooseActionStore.Intent, Unit, ChooseActionStore.State, Unit, ChooseActionStore.Label> =
        ChooseActionExecutor()

    private inner class ChooseActionExecutor :
        BaseExecutor<ChooseActionStore.Intent, Unit, ChooseActionStore.State, Unit, ChooseActionStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> ChooseActionStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: ChooseActionStore.Intent,
            getState: () -> ChooseActionStore.State
        ) {
            when (intent) {
                is ChooseActionStore.Intent.OnTypeClicked -> publish(
                    ChooseActionStore.Label.GoBack(
                        ChooseActionResult(intent.type)
                    )
                )
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(ChooseActionStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private object ReducerImpl : Reducer<ChooseActionStore.State, Unit> {
        override fun ChooseActionStore.State.reduce(result: Unit) = copy()
    }
}
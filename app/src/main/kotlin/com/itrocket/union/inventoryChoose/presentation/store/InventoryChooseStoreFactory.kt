package com.itrocket.union.inventoryChoose.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.inventoryChoose.domain.InventoryChooseActionType

class InventoryChooseStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val errorInteractor: ErrorInteractor,
    private val inventoryChooseArguments: InventoryChooseArguments
) {
    fun create(): InventoryChooseStore =
        object : InventoryChooseStore,
            Store<InventoryChooseStore.Intent, InventoryChooseStore.State, InventoryChooseStore.Label> by storeFactory.create(
                name = "InventoryChooseStore",
                initialState = InventoryChooseStore.State(
                    types = InventoryChooseActionType.values().toList()
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<InventoryChooseStore.Intent, Unit, InventoryChooseStore.State, Unit, InventoryChooseStore.Label> =
        InventoryChooseExecutor()

    private inner class InventoryChooseExecutor :
        BaseExecutor<InventoryChooseStore.Intent, Unit, InventoryChooseStore.State, Unit, InventoryChooseStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> InventoryChooseStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: InventoryChooseStore.Intent,
            getState: () -> InventoryChooseStore.State
        ) {
            when (intent) {
                is InventoryChooseStore.Intent.OnActionClicked -> publish(
                    InventoryChooseStore.Label.GoBack(
                        InventoryChooseResult(
                            type = intent.type,
                            accountingObject = inventoryChooseArguments.accountingObject
                        )
                    )
                )
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(InventoryChooseStore.Label.Error(errorInteractor.getTextMessage(throwable)))
        }
    }

    private object ReducerImpl : Reducer<InventoryChooseStore.State, Unit> {
        override fun InventoryChooseStore.State.reduce(result: Unit) = copy()
    }
}
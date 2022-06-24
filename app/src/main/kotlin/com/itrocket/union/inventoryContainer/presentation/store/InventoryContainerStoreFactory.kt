package com.itrocket.union.inventoryContainer.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.inventoryCreate.presentation.store.InventoryCreateArguments

class InventoryContainerStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoryContainerArguments: InventoryContainerArguments?
) {
    fun create(): InventoryContainerStore =
        object : InventoryContainerStore,
            Store<InventoryContainerStore.Intent, InventoryContainerStore.State, InventoryContainerStore.Label> by storeFactory.create(
                name = "InventoryContainerStore",
                initialState = InventoryContainerStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<InventoryContainerStore.Intent, Unit, InventoryContainerStore.State, Result, InventoryContainerStore.Label> =
        InventoryContainerExecutor()

    private inner class InventoryContainerExecutor :
        BaseExecutor<InventoryContainerStore.Intent, Unit, InventoryContainerStore.State, Result, InventoryContainerStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> InventoryContainerStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: InventoryContainerStore.Intent,
            getState: () -> InventoryContainerStore.State
        ) {
            when (intent) {
                InventoryContainerStore.Intent.OnBackClicked -> publish(InventoryContainerStore.Label.GoBack)
                is InventoryContainerStore.Intent.ShowInventoryCreate -> publish(
                    InventoryContainerStore.Label.ShowInventoryCreate(
                        InventoryCreateArguments(
                            intent.inventoryCreate
                        )
                    )
                )
            }
        }

        override fun handleError(throwable: Throwable) {

        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<InventoryContainerStore.State, Result> {
        override fun InventoryContainerStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
            }
    }
}
package com.itrocket.union.inventoryReserves.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.inventoryReserves.domain.InventoryReservesInteractor
import com.itrocket.union.inventoryReserves.domain.entity.InventoryReservesDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor

class InventoryReservesStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoryReservesInteractor: InventoryReservesInteractor,
    private val inventoryReservesArguments: InventoryReservesArguments
) {
    fun create(): InventoryReservesStore =
        object : InventoryReservesStore,
            Store<InventoryReservesStore.Intent, InventoryReservesStore.State, InventoryReservesStore.Label> by storeFactory.create(
                name = "InventoryReservesStore",
                initialState = InventoryReservesStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<InventoryReservesStore.Intent, Unit, InventoryReservesStore.State, Result, InventoryReservesStore.Label> =
        InventoryReservesExecutor()

    private inner class InventoryReservesExecutor :
        BaseExecutor<InventoryReservesStore.Intent, Unit, InventoryReservesStore.State, Result, InventoryReservesStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> InventoryReservesStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: InventoryReservesStore.Intent,
            getState: () -> InventoryReservesStore.State
        ) {
            when (intent) {
                InventoryReservesStore.Intent.OnBackClicked -> publish(InventoryReservesStore.Label.GoBack)
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(InventoryReservesStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
    }

    private object ReducerImpl : Reducer<InventoryReservesStore.State, Result> {
        override fun InventoryReservesStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
            }
    }
}
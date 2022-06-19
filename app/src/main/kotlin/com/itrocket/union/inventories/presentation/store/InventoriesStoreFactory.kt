package com.itrocket.union.inventories.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.union.inventories.domain.InventoriesInteractor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class InventoriesStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoriesInteractor: InventoriesInteractor
) {
    fun create(): InventoriesStore =
        object : InventoriesStore,
            Store<InventoriesStore.Intent, InventoriesStore.State, InventoriesStore.Label> by storeFactory.create(
                name = "InventoriesStore",
                initialState = InventoriesStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<InventoriesStore.Intent, Unit, InventoriesStore.State, Result, InventoriesStore.Label> =
        InventoriesExecutor()

    private inner class InventoriesExecutor :
        BaseExecutor<InventoriesStore.Intent, Unit, InventoriesStore.State, Result, InventoriesStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> InventoriesStore.State
        ) {
            catchException {
                dispatch(Result.Loading(true))
                inventoriesInteractor.getInventories()
                    .catch {
                        dispatch(Result.Loading(false))
                    }.collect {
                        dispatch(Result.Inventories(it))
                        dispatch(Result.Loading(false))
                    }
            }
        }

        override suspend fun executeIntent(
            intent: InventoriesStore.Intent,
            getState: () -> InventoriesStore.State
        ) {
            when (intent) {
                InventoriesStore.Intent.OnBackClicked -> publish(InventoriesStore.Label.GoBack)
                InventoriesStore.Intent.OnFilterClicked -> publish(InventoriesStore.Label.ShowFilter)
                is InventoriesStore.Intent.OnInventoryClicked -> publish(
                    InventoriesStore.Label.ShowInventoryDetail(
                        intent.inventory
                    )
                )
                InventoriesStore.Intent.OnSearchClicked -> publish(InventoriesStore.Label.ShowSearch)
            }
        }

        override fun handleError(throwable: Throwable) {
            publish(InventoriesStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class Inventories(val inventories: List<InventoryCreateDomain>) : Result()
    }

    private object ReducerImpl : Reducer<InventoriesStore.State, Result> {
        override fun InventoriesStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.Inventories -> copy(inventories = result.inventories)
            }
    }
}
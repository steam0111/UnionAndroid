package com.itrocket.union.inventoryCreate.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.itrocket.union.inventoryCreate.domain.InventoryCreateInteractor
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.core.base.BaseExecutor
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

class InventoryCreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoryCreateInteractor: InventoryCreateInteractor,
    private val inventoryCreateArguments: InventoryCreateArguments
) {
    fun create(): InventoryCreateStore =
        object : InventoryCreateStore,
            Store<InventoryCreateStore.Intent, InventoryCreateStore.State, InventoryCreateStore.Label> by storeFactory.create(
                name = "InventoryCreateStore",
                initialState = InventoryCreateStore.State(
                    accountingObjects = inventoryCreateArguments.accountingObjects,
                    inventoryDocument = inventoryCreateArguments.inventoryDocument
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::createExecutor,
                reducer = ReducerImpl
            ) {}

    private fun createExecutor(): Executor<InventoryCreateStore.Intent, Unit, InventoryCreateStore.State, Result, InventoryCreateStore.Label> =
        InventoryCreateExecutor()

    private inner class InventoryCreateExecutor :
        BaseExecutor<InventoryCreateStore.Intent, Unit, InventoryCreateStore.State, Result, InventoryCreateStore.Label>(
            context = coreDispatchers.ui
        ) {
        override suspend fun executeAction(
            action: Unit,
            getState: () -> InventoryCreateStore.State
        ) {
        }

        override suspend fun executeIntent(
            intent: InventoryCreateStore.Intent,
            getState: () -> InventoryCreateStore.State
        ) {
            when (intent) {
                InventoryCreateStore.Intent.OnBackClicked -> publish(InventoryCreateStore.Label.GoBack)
                is InventoryCreateStore.Intent.OnAccountingObjectClicked -> handleAccountingObjectClicked(
                    accountingObjects = getState().accountingObjects,
                    accountingObject = intent.accountingObject
                )
                InventoryCreateStore.Intent.OnAddNewClicked -> {
                    dispatch(Result.AddNew(!getState().isAddNew))
                }
                InventoryCreateStore.Intent.OnDropClicked -> {
                    drop(getState())
                }
                InventoryCreateStore.Intent.OnHideFoundAccountingObjectClicked -> {
                    dispatch(Result.HideFoundedAccountingObjects(!getState().isHideFoundAccountingObjects))
                }
                InventoryCreateStore.Intent.OnReadingClicked -> {
                    publish(InventoryCreateStore.Label.ShowReading)
                }
                InventoryCreateStore.Intent.OnSaveClicked -> saveInventory(
                    inventoryDocument = getState().inventoryDocument,
                    accountingObjects = getState().accountingObjects
                )
            }
        }

        private fun handleAccountingObjectClicked(
            accountingObjects: List<AccountingObjectDomain>,
            accountingObject: AccountingObjectDomain
        ) {
            if (inventoryCreateInteractor.isNewAccountingObject(
                    accountingObjects = accountingObjects,
                    newAccountingObject = accountingObject
                )
            ) {
                publish(InventoryCreateStore.Label.ShowChangeStatus)
            } else {
                publish(InventoryCreateStore.Label.ShowNewAccountingObjectDetail)
            }
        }

        private fun saveInventory(
            inventoryDocument: InventoryCreateDomain,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            dispatch(Result.Loading(true))
            inventoryCreateInteractor.saveInventoryDocument(
                inventoryDocument,
                accountingObjects
            )
            dispatch(Result.Loading(false))
        }

        private fun drop(state: InventoryCreateStore.State) {
            dispatch(Result.NewAccountingObjects(listOf()))
            dispatch(
                Result.AccountingObjects(
                    inventoryCreateInteractor.dropAccountingObjects(
                        state.accountingObjects
                    )
                )
            )
            dispatch(Result.AddNew(false))
            dispatch(Result.HideFoundedAccountingObjects(false))
        }

        override fun handleError(throwable: Throwable) {
            publish(InventoryCreateStore.Label.Error(throwable.message.orEmpty()))
        }
    }

    private sealed class Result {
        data class Loading(val isLoading: Boolean) : Result()
        data class AddNew(val addNew: Boolean) : Result()
        data class HideFoundedAccountingObjects(val hideFoundedAccountingObjects: Boolean) :
            Result()

        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class NewAccountingObjects(val newAccountingObjects: List<AccountingObjectDomain>) :
            Result()
    }

    private object ReducerImpl : Reducer<InventoryCreateStore.State, Result> {
        override fun InventoryCreateStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.AddNew -> copy(isAddNew = result.addNew)
                is Result.HideFoundedAccountingObjects -> copy(isHideFoundAccountingObjects = result.hideFoundedAccountingObjects)
                is Result.AccountingObjects -> copy(accountingObjects = result.accountingObjects)
                is Result.NewAccountingObjects -> copy(newAccountingObjects = result.newAccountingObjects)
            }
    }
}
package com.itrocket.union.inventoryCreate.presentation.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.itrocket.core.base.BaseExecutor
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventoryCreate.domain.InventoryCreateInteractor
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectArguments
import com.itrocket.union.switcher.domain.entity.SwitcherDomain

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
                    inventoryDocument = inventoryCreateArguments.inventoryDocument,
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
            dispatch(Result.Loading(true))
            catchException {
                dispatch(
                    Result.Inventory(
                        inventoryCreateInteractor.getInventoryById(
                            inventoryCreateArguments.inventoryDocument.number
                        )
                    )
                )
            }
            dispatch(Result.Loading(false))
        }

        override suspend fun executeIntent(
            intent: InventoryCreateStore.Intent,
            getState: () -> InventoryCreateStore.State
        ) {
            when (intent) {
                InventoryCreateStore.Intent.OnBackClicked -> publish(InventoryCreateStore.Label.GoBack)
                is InventoryCreateStore.Intent.OnAccountingObjectClicked -> handleAccountingObjectClicked(
                    accountingObjects = getState().inventoryDocument.accountingObjects,
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
                    accountingObjects = getState().inventoryDocument.accountingObjects
                )
                is InventoryCreateStore.Intent.OnNewAccountingObjectsHandled -> handleNewAccountingObjects(
                    accountingObjects = getState().inventoryDocument.accountingObjects,
                    newAccountingObjects = getState().newAccountingObjects.toList(),
                    handledAccountingObjectIds = intent.handledAccountingObjectIds,
                )
                is InventoryCreateStore.Intent.OnAccountingObjectStatusChanged -> {
                    dispatch(
                        Result.AccountingObjects(
                            inventoryCreateInteractor.changeAccountingObjectInventoryStatus(
                                getState().inventoryDocument.accountingObjects,
                                intent.switcherResult.result
                            )
                        )
                    )
                }
            }
        }

        private suspend fun handleNewAccountingObjects(
            accountingObjects: List<AccountingObjectDomain>,
            handledAccountingObjectIds: List<String>,
            newAccountingObjects: List<AccountingObjectDomain>
        ) {
            dispatch(Result.Loading(true))
            catchException {
                val inventoryAccountingObjects =
                    inventoryCreateInteractor.handleNewAccountingObjects(
                        accountingObjects = accountingObjects,
                        handledAccountingObjectIds = handledAccountingObjectIds
                    )
                dispatch(Result.AccountingObjects(inventoryAccountingObjects.createdAccountingObjects))
                dispatch(
                    Result.NewAccountingObjects((newAccountingObjects + inventoryAccountingObjects.newAccountingObjects).toSet())
                )
            }
            dispatch(Result.Loading(true))
        }

        private fun handleAccountingObjectClicked(
            accountingObjects: List<AccountingObjectDomain>,
            accountingObject: AccountingObjectDomain
        ) {
            if (!inventoryCreateInteractor.isNewAccountingObject(
                    accountingObjects = accountingObjects,
                    newAccountingObject = accountingObject
                )
            ) {
                publish(
                    InventoryCreateStore.Label.ShowChangeStatus(
                        SwitcherDomain(
                            titleId = R.string.switcher_accounting_object_status,
                            values = listOf(
                                InventoryAccountingObjectStatus.NOT_FOUND,
                                InventoryAccountingObjectStatus.FOUND
                            ),
                            currentValue = accountingObject.inventoryStatus,
                            entityId = accountingObject.id
                        )
                    )
                )
            } else {
                publish(
                    InventoryCreateStore.Label.ShowNewAccountingObjectDetail(
                        NewAccountingObjectArguments(
                            accountingObject
                        )
                    )
                )
            }
        }

        private suspend fun saveInventory(
            inventoryDocument: InventoryCreateDomain,
            accountingObjects: List<AccountingObjectDomain>
        ) {
            dispatch(Result.Loading(true))
            catchException {
                inventoryCreateInteractor.saveInventoryDocument(
                    inventoryDocument,
                    accountingObjects
                )
                publish(InventoryCreateStore.Label.GoBack)
            }
            dispatch(Result.Loading(false))
        }

        private fun drop(state: InventoryCreateStore.State) {
            dispatch(Result.NewAccountingObjects(setOf()))
            dispatch(
                Result.AccountingObjects(
                    inventoryCreateInteractor.dropAccountingObjects(
                        state.inventoryDocument.accountingObjects
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
        data class Inventory(val inventory: InventoryCreateDomain) : Result()
        data class AddNew(val addNew: Boolean) : Result()
        data class HideFoundedAccountingObjects(val hideFoundedAccountingObjects: Boolean) :
            Result()

        data class AccountingObjects(val accountingObjects: List<AccountingObjectDomain>) : Result()
        data class NewAccountingObjects(val newAccountingObjects: Set<AccountingObjectDomain>) :
            Result()
    }

    private object ReducerImpl : Reducer<InventoryCreateStore.State, Result> {
        override fun InventoryCreateStore.State.reduce(result: Result) =
            when (result) {
                is Result.Loading -> copy(isLoading = result.isLoading)
                is Result.AddNew -> copy(isAddNew = result.addNew)
                is Result.HideFoundedAccountingObjects -> copy(isHideFoundAccountingObjects = result.hideFoundedAccountingObjects)
                is Result.AccountingObjects -> copy(
                    inventoryDocument = inventoryDocument.copy(
                        accountingObjects = result.accountingObjects
                    )
                )
                is Result.NewAccountingObjects -> copy(newAccountingObjects = result.newAccountingObjects)
                is Result.Inventory -> copy(inventoryDocument = result.inventory)
            }
    }
}
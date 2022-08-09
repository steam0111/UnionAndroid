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
import com.itrocket.union.error.ErrorInteractor
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventoryCreate.domain.InventoryCreateInteractor
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectArguments
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import com.itrocket.union.utils.ifBlankOrNull

class InventoryCreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val coreDispatchers: CoreDispatchers,
    private val inventoryCreateInteractor: InventoryCreateInteractor,
    private val inventoryCreateArguments: InventoryCreateArguments,
    private val errorInteractor: ErrorInteractor
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
                            inventoryCreateArguments.inventoryDocument.id.orEmpty()
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
                    publish(InventoryCreateStore.Label.ShowReadingMode)
                }
                InventoryCreateStore.Intent.OnSaveClicked -> saveInventory(
                    inventoryDocument = getState().inventoryDocument,
                    accountingObjects = getState().inventoryDocument.accountingObjects + getState().newAccountingObjects
                )
                is InventoryCreateStore.Intent.OnNewAccountingObjectBarcodeHandled -> {
                    val inventoryStatus = getState().inventoryDocument.inventoryStatus
                    if (inventoryStatus != InventoryStatus.COMPLETED) {
                        handleNewAccountingObjectBarcode(
                            accountingObjects = getState().inventoryDocument.accountingObjects,
                            newAccountingObjects = getState().newAccountingObjects.toList(),
                            barcode = intent.barcode,
                            inventoryStatus = inventoryStatus,
                            isAddNew = getState().isAddNew
                        )
                    }
                }
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
                is InventoryCreateStore.Intent.OnNewAccountingObjectRfidHandled -> {
                    val inventoryStatus = getState().inventoryDocument.inventoryStatus
                    if (inventoryStatus != InventoryStatus.COMPLETED) {
                        handleNewAccountingObjectRfids(
                            accountingObjects = getState().inventoryDocument.accountingObjects,
                            newAccountingObjects = getState().newAccountingObjects.toList(),
                            handledAccountingObjectId = intent.handledAccountingObjectId,
                            inventoryStatus = inventoryStatus,
                            isAddNew = getState().isAddNew
                        )
                    }
                }
                InventoryCreateStore.Intent.OnCompleteClicked -> completeInventory(inventoryDomain = getState().inventoryDocument)
                InventoryCreateStore.Intent.OnInWorkClicked -> inWorkInventory(
                    inventoryDomain = getState().inventoryDocument,
                    newAccountingObjects = getState().newAccountingObjects.toList()
                )
            }
        }

        private suspend fun completeInventory(
            inventoryDomain: InventoryCreateDomain
        ) {
            val inventory = inventoryDomain.copy(inventoryStatus = InventoryStatus.COMPLETED)
            dispatch(Result.Inventory(inventory))
            inventoryCreateInteractor.saveInventoryDocument(inventory, inventory.accountingObjects)
        }

        private suspend fun inWorkInventory(
            inventoryDomain: InventoryCreateDomain,
            newAccountingObjects: List<AccountingObjectDomain>
        ) {
            val accountingObjects = inventoryCreateInteractor.makeInInventoryAccountingObjects(
                accountingObjects = inventoryDomain.accountingObjects,
                newAccountingObjects = newAccountingObjects
            )
            val inventory = inventoryDomain.copy(
                inventoryStatus = InventoryStatus.IN_PROGRESS,
                accountingObjects = accountingObjects
            )
            inventoryCreateInteractor.saveInventoryDocument(
                inventoryCreate = inventory,
                accountingObjects = accountingObjects
            )
            dispatch(Result.NewAccountingObjects(setOf()))
            dispatch(Result.Inventory(inventory))
        }

        private suspend fun handleNewAccountingObjectRfids(
            accountingObjects: List<AccountingObjectDomain>,
            handledAccountingObjectId: String,
            newAccountingObjects: List<AccountingObjectDomain>,
            inventoryStatus: InventoryStatus,
            isAddNew: Boolean
        ) {
            dispatch(Result.Loading(true))
            catchException {
                val inventoryAccountingObjects =
                    inventoryCreateInteractor.handleNewAccountingObjectRfids(
                        accountingObjects = accountingObjects,
                        handledAccountingObjectId = handledAccountingObjectId,
                        newAccountingObjects = newAccountingObjects,
                        inventoryStatus = inventoryStatus,
                        isAddNew = isAddNew
                    )
                dispatch(Result.AccountingObjects(inventoryAccountingObjects.createdAccountingObjects))
                dispatch(
                    Result.NewAccountingObjects((newAccountingObjects + inventoryAccountingObjects.newAccountingObjects).toSet())
                )
            }
            dispatch(Result.Loading(true))
        }

        private suspend fun handleNewAccountingObjectBarcode(
            accountingObjects: List<AccountingObjectDomain>,
            barcode: String,
            newAccountingObjects: List<AccountingObjectDomain>,
            inventoryStatus: InventoryStatus,
            isAddNew: Boolean
        ) {
            dispatch(Result.Loading(true))
            catchException {
                val inventoryAccountingObjects =
                    inventoryCreateInteractor.handleNewAccountingObjectBarcode(
                        accountingObjects = accountingObjects,
                        barcode = barcode,
                        newAccountingObjects = newAccountingObjects,
                        inventoryStatus = inventoryStatus,
                        isAddNew = isAddNew
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
                            accountingObject.id
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
            publish(InventoryCreateStore.Label.Error(throwable.message.ifBlankOrNull { errorInteractor.getDefaultError() }))
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
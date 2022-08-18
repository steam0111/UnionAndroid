package com.itrocket.union.inventoryCreate.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectsDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.domain.entity.toUpdateSyncEntity
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import kotlinx.coroutines.withContext

class InventoryCreateInteractor(
    private val accountingObjectRepository: AccountingObjectRepository,
    private val inventoryRepository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor
) {

    suspend fun getInventoryById(id: String) =
        withContext(coreDispatchers.io) {
            inventoryRepository.getInventoryById(id)
        }

    suspend fun saveInventoryDocument(
        inventoryCreate: InventoryCreateDomain,
        accountingObjects: List<AccountingObjectDomain>
    ) = withContext(coreDispatchers.io) {
        val newAccountingObjects = accountingObjects.map {
            if (it.inventoryStatus == InventoryAccountingObjectStatus.NEW) {
                it.copy(inventoryStatus = InventoryAccountingObjectStatus.FOUND)
            } else {
                it
            }
        }
        inventoryRepository.updateInventory(
            inventoryCreate.copy(
                accountingObjects = newAccountingObjects,
                userUpdated = authMainInteractor.getLogin()
            ).toUpdateSyncEntity()
        )
    }

    suspend fun handleNewAccountingObjectRfids(
        accountingObjects: List<AccountingObjectDomain>,
        newAccountingObjects: List<AccountingObjectDomain>,
        handledAccountingObjectId: String,
        inventoryStatus: InventoryStatus,
        isAddNew: Boolean
    ): InventoryAccountingObjectsDomain {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectRfids = mutableListOf<String>()
            val mutableAccountingObjects = accountingObjects.toMutableList()

            val accountingObjectIndex = mutableAccountingObjects.indexOfFirst {
                it.rfidValue == handledAccountingObjectId
            }
            val newAccountingObjectIndex =
                newAccountingObjects.indexOfFirst { it.rfidValue == handledAccountingObjectId }

            when {
                accountingObjectIndex != NO_INDEX -> changeStatusByIndex(
                    mutableAccountingObjects,
                    accountingObjectIndex
                )
                newAccountingObjectIndex == NO_INDEX && inventoryStatus != InventoryStatus.COMPLETED && isAddNew -> newAccountingObjectRfids.add(
                    handledAccountingObjectId
                )
            }

            val handledAccountingObjects =
                getHandlesAccountingObjectByRfid(newAccountingObjectRfids)
            InventoryAccountingObjectsDomain(
                newAccountingObjects = handledAccountingObjects,
                createdAccountingObjects = mutableAccountingObjects
            )
        }
    }

    suspend fun handleNewAccountingObjectBarcode(
        accountingObjects: List<AccountingObjectDomain>,
        barcode: String,
        newAccountingObjects: List<AccountingObjectDomain>,
        inventoryStatus: InventoryStatus,
        isAddNew: Boolean
    ): InventoryAccountingObjectsDomain {
        return withContext(coreDispatchers.io) {
            val barcodeAccountingObjects = mutableListOf<AccountingObjectDomain>()
            val mutableAccountingObjects = accountingObjects.toMutableList()

            val accountingObjectIndex = mutableAccountingObjects.indexOfFirst {
                it.barcodeValue == barcode
            }
            val newAccountingObjectIndex =
                newAccountingObjects.indexOfFirst { it.barcodeValue == barcode }

            when {
                accountingObjectIndex != NO_INDEX -> changeStatusByIndex(
                    mutableAccountingObjects,
                    accountingObjectIndex
                )
                newAccountingObjectIndex == NO_INDEX && inventoryStatus != InventoryStatus.COMPLETED && isAddNew -> {
                    val accountingObjectDomain = getHandleAccountingObjectByBarcode(barcode)
                    if (accountingObjectDomain != null) {
                        barcodeAccountingObjects.add(accountingObjectDomain)
                    }
                }
            }

            InventoryAccountingObjectsDomain(
                newAccountingObjects = barcodeAccountingObjects,
                createdAccountingObjects = mutableAccountingObjects
            )
        }
    }

    fun dropAccountingObjects(accountingObjects: List<AccountingObjectDomain>): List<AccountingObjectDomain> {
        return accountingObjects.map { it.copy() }
    }

    fun changeAccountingObjectInventoryStatus(
        accountingObjects: List<AccountingObjectDomain>,
        switcherDomain: SwitcherDomain
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjects.toMutableList()
        val newStatus = switcherDomain.currentValue
        val index = accountingObjects.indexOfFirst { it.id == switcherDomain.entityId }
        mutableList[index] =
            mutableList[index].copy(inventoryStatus = newStatus as InventoryAccountingObjectStatus)
        return mutableList
    }

    fun isNewAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        newAccountingObject: AccountingObjectDomain
    ): Boolean {
        return !accountingObjects.contains(newAccountingObject)
    }

    fun makeInInventoryAccountingObjects(
        accountingObjects: List<AccountingObjectDomain>,
        newAccountingObjects: List<AccountingObjectDomain>
    ): List<AccountingObjectDomain> {
        return accountingObjects + newAccountingObjects.map {
            it.copy(inventoryStatus = InventoryAccountingObjectStatus.FOUND)
        }
    }

    private suspend fun getHandlesAccountingObjectByRfid(rfids: List<String>): List<AccountingObjectDomain> {
        return accountingObjectRepository.getAccountingObjectsByRfids(rfids)
            .map {
                it.copy(inventoryStatus = InventoryAccountingObjectStatus.NEW)
            }
    }

    private suspend fun getHandleAccountingObjectByBarcode(barcode: String): AccountingObjectDomain? {
        return accountingObjectRepository.getAccountingObjectsByBarcode(barcode)
            ?.copy(inventoryStatus = InventoryAccountingObjectStatus.NEW)

    }

    private fun changeStatusByIndex(
        accountingObjects: MutableList<AccountingObjectDomain>,
        index: Int
    ) {
        val accountingObject = accountingObjects[index]
        accountingObjects[index] =
            accountingObject.copy(inventoryStatus = InventoryAccountingObjectStatus.FOUND)
    }


    companion object {
        private const val NO_INDEX = -1
    }
}
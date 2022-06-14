package com.itrocket.union.inventoryCreate.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.inventoryCreate.domain.dependencies.InventoryCreateRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectsDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryStatus

class InventoryCreateInteractor(
    private val repository: InventoryCreateRepository,
    private val coreDispatchers: CoreDispatchers
) {

    fun saveInventoryDocument(
        inventoryCreate: InventoryCreateDomain,
        accountingObjects: List<AccountingObjectDomain>
    ) {
        // map in entity and save in db
    }

    suspend fun handleNewAccountingObjects(
        accountingObjects: List<AccountingObjectDomain>,
        handledAccountingObjectIds: List<String>
    ): InventoryAccountingObjectsDomain {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectIds = mutableListOf<String>()
            val mutableAccountingObjects = accountingObjects.toMutableList()
            handledAccountingObjectIds.forEach { id ->
                val index = mutableAccountingObjects.indexOfFirst { it.id == id }
                if (index != NO_INDEX) {
                    mutableAccountingObjects[index] =
                        mutableAccountingObjects[index].copy(inventoryStatus = InventoryStatus.FOUND)
                } else {
                    newAccountingObjectIds.add(id)
                }
            }
            val newAccountingObjects =
                repository.getAccountingObjectsByIds(newAccountingObjectIds)
            InventoryAccountingObjectsDomain(newAccountingObjects = newAccountingObjects, createdAccountingObjects = mutableAccountingObjects)
        }
    }

    fun dropAccountingObjects(accountingObjects: List<AccountingObjectDomain>): List<AccountingObjectDomain> {
        return accountingObjects.map { it.copy() }
    }

    fun isNewAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        newAccountingObject: AccountingObjectDomain
    ): Boolean {
        return accountingObjects.contains(newAccountingObject)
    }

    companion object {
        private const val NO_INDEX = -1
    }
}
package com.itrocket.union.inventoryCreate.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.inventoryCreate.domain.dependencies.InventoryCreateRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain

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

    fun dropAccountingObjects(accountingObjects: List<AccountingObjectDomain>): List<AccountingObjectDomain> {
        return accountingObjects.map { it.copy() }
    }

    fun isNewAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        newAccountingObject: AccountingObjectDomain
    ): Boolean {
        return accountingObjects.contains(newAccountingObject)
    }
}
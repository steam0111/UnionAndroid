package com.itrocket.union.inventoryCreate.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.inventoryCreate.domain.dependencies.InventoryCreateRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectResult

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

    fun addNewAccountingObject(
        newAccountingObjects: List<AccountingObjectDomain>,
        newAccountingObject: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        val mutableList = newAccountingObjects.toMutableList()
        mutableList.add(0, newAccountingObject)
        return mutableList
    }

    fun dropAccountingObjects(accountingObjects: List<AccountingObjectDomain>): List<AccountingObjectDomain> {
        return accountingObjects.map { it.copy() }
    }
}
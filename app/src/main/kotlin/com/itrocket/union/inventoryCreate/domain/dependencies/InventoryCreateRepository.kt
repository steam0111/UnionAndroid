package com.itrocket.union.inventoryCreate.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain

interface InventoryCreateRepository {
    suspend fun getAccountingObjectsByIds(listAccountingObjectIds: List<String>): List<AccountingObjectDomain>
}
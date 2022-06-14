package com.itrocket.union.inventoryCreate.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

data class InventoryAccountingObjectsDomain(
    val createdAccountingObjects: List<AccountingObjectDomain>,
    val newAccountingObjects: List<AccountingObjectDomain>
)
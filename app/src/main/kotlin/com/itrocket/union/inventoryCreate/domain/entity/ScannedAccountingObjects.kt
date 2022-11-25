package com.itrocket.union.inventoryCreate.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

data class ScannedAccountingObjects(
    val accountingObjects: List<AccountingObjectDomain>,
    val hasWrittenOffAccountingObjects: Boolean
)
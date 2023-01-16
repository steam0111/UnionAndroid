package com.itrocket.union.inventoryCreate.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventory.domain.entity.InventoryNomenclatureDomain

data class ScannedAccountingObjects(
    val accountingObjects: List<AccountingObjectDomain>,
    val inventoryNomenclatures: List<InventoryNomenclatureDomain>,
    val hasWrittenOffAccountingObjects: Boolean
)
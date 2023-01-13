package com.example.union_sync_api.entity

import java.math.BigDecimal

data class InventoryNomenclatureRecordSyncEntity(
    val id: String,
    val inventoryId: String,
    val nomenclatureId: String,
    val updateDate: Long?,
    val expectedCount: Long?,
    val actualCount: Long?,
    val consignment: String?,
    val bookKeepingInvoice: BigDecimal?,
    val price: String?,
    val cancel: Boolean?,
    val userUpdated: String? = null
)
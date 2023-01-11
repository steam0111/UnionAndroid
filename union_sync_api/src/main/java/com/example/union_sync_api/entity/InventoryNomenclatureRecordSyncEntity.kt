package com.example.union_sync_api.entity

data class InventoryNomenclatureRecordSyncEntity(
    val id: String,
    val inventoryId: String,
    val nomenclatureId: String,
    val updateDate: Long?,
    val expectedCount: Long?,
    val actualCount: Long?,
    val consignment: String?,
    val bookKeepingInvoice: Long?,
    val price: String?,
)
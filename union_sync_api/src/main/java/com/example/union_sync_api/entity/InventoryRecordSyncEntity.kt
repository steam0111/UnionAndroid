package com.example.union_sync_api.entity

data class InventoryRecordSyncEntity(
    val id: String,
    val inventoryId: String,
    val accountingObjectId: String,
    val updateDate: Long?
)
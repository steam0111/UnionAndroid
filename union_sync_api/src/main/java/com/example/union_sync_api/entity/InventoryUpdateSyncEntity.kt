package com.example.union_sync_api.entity

data class InventoryUpdateSyncEntity(
    val id: Long,
    val organizationId: String,
    val employeeId: String,
    val accountingObjectsIds: List<String>,
    val date: Long
)
package com.example.union_sync_api.entity

data class InventoryCreateSyncEntity(
    val organizationId: String,
    val employeeId: String?,
    val accountingObjectsIds: List<AccountingObjectInfoSyncEntity>,
    val locationIds: List<String>?
)
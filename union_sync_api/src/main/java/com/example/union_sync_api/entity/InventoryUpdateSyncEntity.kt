package com.example.union_sync_api.entity

data class InventoryUpdateSyncEntity(
    val id: String,
    val organizationId: String?,
    val employeeId: String?,
    val accountingObjectsIds: List<AccountingObjectInfoSyncEntity>,
    val locationIds: List<String>?,
    val inventoryStatus: String,
    val date: Long?,
    val updateDate: Long,
    val code: String? = null,
    val name: String? = null
)
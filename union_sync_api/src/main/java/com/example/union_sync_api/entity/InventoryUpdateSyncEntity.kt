package com.example.union_sync_api.entity

data class InventoryUpdateSyncEntity(
    val id: String,
    val structuralId: String?,
    val employeeId: String?,
    val accountingObjectsIds: List<AccountingObjectInfoSyncEntity>,
    val locationIds: List<String>?,
    val inventoryStatus: String,
    val date: Long?,
    val updateDate: Long,
    val code: String? = null,
    val name: String? = null,
    val userInserted: String?,
    val userUpdated: String?,
    val inventoryBaseId: String?
)
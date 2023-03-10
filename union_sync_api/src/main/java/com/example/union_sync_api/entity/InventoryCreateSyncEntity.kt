package com.example.union_sync_api.entity

data class InventoryCreateSyncEntity(
    val employeeId: String?,
    val locationIds: List<String>?,
    val inventoryStatus: String,
    val code: String? = null,
    val name: String? = null,
    val userInserted: String?,
    val userUpdated: String?,
    val structuralId: String?,
    val inventoryBaseId: String?
)
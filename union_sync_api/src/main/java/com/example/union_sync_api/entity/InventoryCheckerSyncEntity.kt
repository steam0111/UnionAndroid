package com.example.union_sync_api.entity

class InventoryCheckerSyncEntity(
    val inventoryId: String,
    val employeeId: String,
    val employee: EmployeeSyncEntity
)
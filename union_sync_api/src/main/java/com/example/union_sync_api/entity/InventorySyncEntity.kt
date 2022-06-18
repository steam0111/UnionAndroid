package com.example.union_sync_api.entity

data class InventorySyncEntity(
    val id: String,
    val date: String,
    val organizationSyncEntity: OrganizationSyncEntity,
    val mol: EmployeeSyncEntity
)
package com.example.union_sync_api.entity

data class InventorySyncEntity(
    val id: String,
    val date: Long,
    val organizationSyncEntity: OrganizationSyncEntity,
    val mol: EmployeeSyncEntity,
    val locationSyncEntities: List<LocationShortSyncEntity>
)
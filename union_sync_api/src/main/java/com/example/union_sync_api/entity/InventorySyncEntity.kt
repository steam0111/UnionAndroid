package com.example.union_sync_api.entity

data class InventorySyncEntity(
    val id: String,
    val date: Long?,
    val organizationSyncEntity: OrganizationSyncEntity?,
    val accountingObjects: List<AccountingObjectSyncEntity>,
    val mol: EmployeeSyncEntity?,
    val locationSyncEntities: List<LocationSyncEntity>?,
    val inventoryStatus: String,
    val updateDate: Long?
)
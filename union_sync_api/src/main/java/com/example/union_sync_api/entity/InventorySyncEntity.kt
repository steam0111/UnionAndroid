package com.example.union_sync_api.entity

data class InventorySyncEntity(
    val id: String,
    val creationDate: Long?,
    val structuralSyncEntities: List<StructuralSyncEntity>?,
    val accountingObjects: List<AccountingObjectSyncEntity>,
    val mol: EmployeeSyncEntity?,
    val locationSyncEntities: List<LocationSyncEntity>?,
    val inventoryStatus: String,
    val updateDate: Long?,
    val code: String?,
    val name: String?,
    val userInserted: String?,
    val userUpdated: String?,
    val inventoryBaseSyncEntity: EnumSyncEntity?,
    val balanceUnit: List<StructuralSyncEntity>,
    val checkers: List<InventoryCheckerSyncEntity>?
)
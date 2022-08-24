package com.example.union_sync_api.entity

data class AccountingObjectDetailSyncEntity(
    val accountingObject: AccountingObjectSyncEntity,
    val location: List<LocationSyncEntity>?,
    val mol: EmployeeSyncEntity?,
    val exploitingEmployee: EmployeeSyncEntity?,
    val producer: ProducerSyncEntity?,
    val equipmentType: EquipmentTypeSyncEntity?,
    val provider: ProviderSyncEntity?,
    val structuralSyncEntities: List<StructuralSyncEntity>?,
    val categorySyncEntity: AccountingObjectCategorySyncEntity?,
    val balanceUnitSyncEntities: List<StructuralSyncEntity>?
)
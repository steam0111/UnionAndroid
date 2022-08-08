package com.example.union_sync_api.entity

data class AccountingObjectDetailSyncEntity(
    val accountingObject: AccountingObjectSyncEntity,
    val location: LocationSyncEntity?,
    val mol: EmployeeSyncEntity?,
    val exploitingEmployee: EmployeeSyncEntity?,
    val producer: ProducerSyncEntity?,
    val equipmentType: EquipmentTypeSyncEntity?,
    val provider: ProviderSyncEntity?,
    val structuralSyncEntity: StructuralSyncEntity?
)
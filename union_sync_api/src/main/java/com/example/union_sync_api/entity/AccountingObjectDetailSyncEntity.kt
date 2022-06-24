package com.example.union_sync_api.entity

data class AccountingObjectDetailSyncEntity(
    val accountingObject: AccountingObjectSyncEntity,
    val location: LocationSyncEntity?,
    val mol: EmployeeSyncEntity?,
    val exploitingEmployee: EmployeeSyncEntity?,
    val organization: OrganizationSyncEntity?,
    val department: DepartmentSyncEntity?,
    val producer: ProducerSyncEntity?,
    val equipmentType: EquipmentTypesSyncEntity?,
    val provider: ProviderSyncEntity?
)
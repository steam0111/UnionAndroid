package com.example.union_sync_api.entity

class AccountingObjectSyncEntity(
    val id: String,
    var catalogItemName: String,
    val barcodeValue: String?,
    val name: String,
    val rfidValue: String?,
    val factoryNumber: String?,
    val inventoryNumber: String?,
    val locationSyncEntity: LocationSyncEntity?,
    val status: AccountingObjectStatus?,
    val mol: EmployeeSyncEntity?,
    val exploitingEmployee: EmployeeSyncEntity?,
    val organizationSyncEntity: OrganizationSyncEntity?,
    val actualPrice: String?,
    val producerSyncEntity: ProducerSyncEntity?,
    val equipmentTypesSyncEntity: EquipmentTypesSyncEntity?,
    val providerSyncEntity: ProviderSyncEntity?,
    val count: Int?,
    val commissioningDate: String?,
    val internalNumber: String?,
    val departmentSyncEntity: DepartmentSyncEntity?,
    val inventoryStatus: String? = null
)

class AccountingObjectStatus(
    val id: String,
    val name: String
)

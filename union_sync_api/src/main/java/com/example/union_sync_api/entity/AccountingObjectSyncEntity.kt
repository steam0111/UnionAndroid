package com.example.union_sync_api.entity

class AccountingObjectSyncEntity(
    val id: String,
    var catalogItemName: String,
    val barcodeValue: String?,
    val name: String,
    val rfidValue: String?,
    val factoryNumber: String?,
    val inventoryNumber: String?,
    val actualPrice: String?,
    val count: Int?,
    val commissioningDate: String?,
    val internalNumber: String?,
    val inventoryStatus: String? = null,
    val status: AccountingObjectStatusSyncEntity?,
    val statusId: String?,
    val providerId: String?,
    val departmentId: String?,
    val producerId: String?,
    val equipmentTypeId: String?,
    val locationId: String?,
    val molId: String?,
    val exploitingEmployeeId: String?,
    val organizationId: String?,
    val model: String?,
    val locationSyncEntity: LocationSyncEntity?
)

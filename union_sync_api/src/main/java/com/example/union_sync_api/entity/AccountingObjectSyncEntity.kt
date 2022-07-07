package com.example.union_sync_api.entity

data class AccountingObjectSyncEntity(
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
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
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

fun AccountingObjectSyncEntity.toAccountingObjectUpdateSyncEntity(): AccountingObjectUpdateSyncEntity {
    return AccountingObjectUpdateSyncEntity(
        id = id,
        locationId = locationId,
        exploitingId = exploitingEmployeeId,
        status = status,
        statusId = statusId,
        updateDate = System.currentTimeMillis()
    )
}

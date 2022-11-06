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
    val status: EnumSyncEntity?,
    val statusId: String?,
    val providerId: String?,
    val producerId: String?,
    val equipmentTypeId: String?,
    val locationId: String?,
    val molId: String?,
    val exploitingEmployeeId: String?,
    val model: String?,
    val locationSyncEntity: List<LocationSyncEntity>?,
    val structuralId: String?,
    val subName: String?,
    val code: String?,
    val marked: Boolean,
    val forWriteOff: Boolean,
    val writtenOff: Boolean,
    val commissioned: Boolean,
    val registered: Boolean,
    val traceable: Boolean,
    val invoiceNumber: String?,
    val nfc: String?,
    val userUpdated: String?,
    var userInserted: String?,
    val dateInsert: Long?,
    var updateDate: Long?,
    val comment: String? = null,
    val manualInput: Boolean? = null
)

fun AccountingObjectSyncEntity.toAccountingObjectUpdateSyncEntity(userUpdated: String?): AccountingObjectUpdateSyncEntity {
    return AccountingObjectUpdateSyncEntity(
        id = id,
        locationId = locationId,
        exploitingId = exploitingEmployeeId,
        status = status,
        statusId = statusId,
        updateDate = System.currentTimeMillis(),
        structuralId = structuralId,
        molId = molId,
        userUpdated = userUpdated
    )
}

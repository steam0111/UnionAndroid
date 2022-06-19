package com.example.union_sync_api.entity

class AccountingObjectSyncEntity(
    val id: String,
    var catalogItemName: String,
    val organizationId: String?,
    val barcodeValue: String?,
    val locationId: String?,
    val molId: String?,
    val exploitingId: String?,
    val nomenclatureId: String?,
    val nomenclatureGroupId: String?,
    val name: String,
    val rfidValue: String?,
    val factoryNumber: String?,
    val inventoryNumber: String?,
    val locationSyncEntity: LocationSyncEntity?,
    val status: AccountingObjectStatus?
)

class AccountingObjectStatus(
    val id: String,
    val name: String
)

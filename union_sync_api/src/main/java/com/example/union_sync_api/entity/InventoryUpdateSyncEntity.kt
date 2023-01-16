package com.example.union_sync_api.entity

data class InventoryUpdateSyncEntity(
    val id: String,
    val structuralId: String?,
    val employeeId: String?,
    val accountingObjectsIds: List<AccountingObjectInfoSyncEntity>,
    val nomenclatureRecords: List<InventoryNomenclatureRecordSyncEntity>,
    val locationIds: List<String>?,
    val inventoryStatus: String,
    val creationDate: Long?,
    val code: String? = null,
    val name: String? = null,
    val userInserted: String?,
    val userUpdated: String?,
    val inventoryBaseId: String?,
    val rfids: List<String>
)
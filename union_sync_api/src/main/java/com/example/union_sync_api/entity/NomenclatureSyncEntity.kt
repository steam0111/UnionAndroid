package com.example.union_sync_api.entity

class NomenclatureSyncEntity(
    val id: String,
    val catalogItemName: String,
    val name: String,
    val code: String?,
    val nomenclatureGroupId: String?,
    val userUpdated: String?,
    var userInserted: String?,
    val dateInsert: Long?,
    var updateDate: Long?,
    val barcode: String?,
)
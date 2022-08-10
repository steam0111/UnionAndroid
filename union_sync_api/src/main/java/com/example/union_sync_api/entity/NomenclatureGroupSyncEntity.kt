package com.example.union_sync_api.entity

class NomenclatureGroupSyncEntity(
    val id: String,
    val name: String,
    val code: String?,
    val catalogItemName: String,
    val userUpdated: String?,
    var userInserted: String?,
    val dateInsert: Long?,
    var updateDate: Long?,
)
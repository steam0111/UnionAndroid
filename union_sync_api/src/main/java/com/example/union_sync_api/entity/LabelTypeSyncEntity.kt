package com.example.union_sync_api.entity

data class LabelTypeSyncEntity(
    val id: String,
    val catalogItemName: String,
    val name: String?,
    val description: String?,
    val code: String?,
    val userUpdated: String?,
    var userInserted: String?,
    val dateInsert: Long?,
    var updateDate: Long?,
)
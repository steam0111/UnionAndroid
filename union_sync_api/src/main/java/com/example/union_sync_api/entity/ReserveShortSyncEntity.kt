package com.example.union_sync_api.entity

data class ReserveShortSyncEntity(
    val locationId: String?,
    val nomenclatureId: String?,
    val orderId: String?,
    val name: String,
    val userUpdated: String?,
    val updateDate: Long
)
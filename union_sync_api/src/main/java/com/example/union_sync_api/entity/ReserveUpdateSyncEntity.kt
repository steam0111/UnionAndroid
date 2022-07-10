package com.example.union_sync_api.entity

data class ReserveUpdateSyncEntity(
    val id: String,
    val count: Long,
    val locationId: String?
)
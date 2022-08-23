package com.example.union_sync_api.entity

data class DocumentUpdateReservesSyncEntity(
    val id: String,
    val reservesIds: List<ReserveCountSyncEntity>,
    val userUpdated: String?
)
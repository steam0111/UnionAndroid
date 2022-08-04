package com.example.union_sync_api.entity

data class DocumentReserveCountSyncEntity(
    val id: String,
    val count: Long?,
    val userUpdated: String?
)
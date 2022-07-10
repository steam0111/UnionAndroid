package com.example.union_sync_api.data

import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity

interface DocumentReserveCountSyncApi {

    suspend fun getAll(reserveIds: List<String>? = null): List<DocumentReserveCountSyncEntity>

    suspend fun insertAll(documentReserveCountSyncEntities: List<DocumentReserveCountSyncEntity>)
}
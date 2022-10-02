package com.example.union_sync_api.data

import com.example.union_sync_api.entity.SyncEvent
import kotlinx.coroutines.flow.Flow

interface SyncEventsApi {
    suspend fun emit(syncEvent: SyncEvent)

    fun subscribe(): Flow<SyncEvent>
}
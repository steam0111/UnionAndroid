package com.example.union_sync_api.data

import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_api.entity.SyncInfoType
import kotlinx.coroutines.flow.Flow

interface SyncEventsApi {
    suspend fun emitSyncEvent(syncEvent: SyncEvent)

    suspend fun emitSyncInfoType(syncInfoType: SyncInfoType)

    fun subscribeSyncInfoType(): Flow<SyncInfoType>

    fun subscribeSyncEvent(): Flow<SyncEvent>
}
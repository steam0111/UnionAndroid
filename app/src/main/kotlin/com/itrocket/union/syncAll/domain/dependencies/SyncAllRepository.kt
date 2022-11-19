package com.itrocket.union.syncAll.domain.dependencies

import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_api.entity.SyncInfoType
import kotlinx.coroutines.flow.Flow

interface SyncAllRepository {
    suspend fun syncAll()

    suspend fun clearAll()

    suspend fun getLastSyncTime(): Long

    fun subscribeSyncEvents(): Flow<SyncEvent>

    fun subscribeSyncInfoType(): Flow<SyncInfoType>
}
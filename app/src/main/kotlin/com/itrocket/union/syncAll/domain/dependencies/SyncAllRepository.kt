package com.itrocket.union.syncAll.domain.dependencies

import com.example.union_sync_api.entity.SyncEvent
import kotlinx.coroutines.flow.Flow

interface SyncAllRepository {
    suspend fun syncAll()

    suspend fun clearAll()

    fun subscribeSyncEvents(): Flow<SyncEvent>
}
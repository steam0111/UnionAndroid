package com.itrocket.union.syncAll.domain

import com.example.union_sync_api.entity.SyncEvent
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SyncAllInteractor(
    private val repository: SyncAllRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun syncAll() = withContext(coreDispatchers.io) {
        repository.syncAll()
    }

    suspend fun clearAll() =
        withContext(coreDispatchers.io) {
            repository.clearAll()
        }

    fun subscribeSyncEvents(): Flow<SyncEvent> {
        return repository.subscribeSyncEvents()
    }
}
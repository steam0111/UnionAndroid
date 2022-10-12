package com.itrocket.union.syncAll.domain

import com.example.union_sync_api.entity.SyncEvent
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SyncAllInteractor(
    private val repository: SyncAllRepository,
    private val authMainRepository: AuthMainRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun updateMyConfig() {
        withContext(coreDispatchers.io) {
            val config = authMainRepository.getMyConfig()
            authMainRepository.saveMyConfig(config)
        }
    }

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
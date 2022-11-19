package com.itrocket.union.syncAll.data

import com.example.union_sync_api.data.AllSyncApi
import com.example.union_sync_api.data.ManageSyncDataApi
import com.example.union_sync_api.data.SyncEventsApi
import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_api.entity.SyncInfoType
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SyncAllRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AllSyncApi,
    private val manageSyncDataApi: ManageSyncDataApi,
    private val syncEventsApi: SyncEventsApi
) : SyncAllRepository {

    override suspend fun syncAll(): Unit = withContext(coreDispatchers.io) {
        syncApi.syncAll()
    }

    override suspend fun clearAll() {
        withContext(coreDispatchers.io) {
            manageSyncDataApi.clearAll()
        }
    }

    override suspend fun getLastSyncDate(): Long {
        return withContext(coreDispatchers.io) {
            syncApi.getLastSyncTime()
        }
    }

    override fun subscribeSyncEvents(): Flow<SyncEvent> {
        return syncEventsApi.subscribeSyncEvent().flowOn(coreDispatchers.io)
    }

    override fun subscribeSyncInfoType(): Flow<SyncInfoType> {
        return syncEventsApi.subscribeSyncInfoType().flowOn(coreDispatchers.io)
    }
}
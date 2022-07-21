package com.itrocket.union.syncAll.data

import com.example.union_sync_api.data.AllSyncApi
import com.example.union_sync_api.data.ManageSyncDataApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import kotlinx.coroutines.withContext

class SyncAllRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AllSyncApi,
    private val manageSyncDataApi: ManageSyncDataApi
) : SyncAllRepository {

    override suspend fun syncAll(): Unit = withContext(coreDispatchers.io) {
        syncApi.syncAll()
    }

    override suspend fun clearAll() {
        withContext(coreDispatchers.io) {
            manageSyncDataApi.clearAll()
        }
    }
}
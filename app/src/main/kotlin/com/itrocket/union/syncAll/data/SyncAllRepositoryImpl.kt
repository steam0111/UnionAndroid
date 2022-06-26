package com.itrocket.union.syncAll.data

import com.example.union_sync_api.data.AllSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import kotlinx.coroutines.withContext

class SyncAllRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AllSyncApi,
) : SyncAllRepository {

    override suspend fun syncAll(): Unit = withContext(coreDispatchers.io) {
        syncApi.syncAll()
    }
}
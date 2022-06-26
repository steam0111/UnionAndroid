package com.itrocket.union.container.domain

import com.example.union_sync_api.data.AllSyncApi
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.withContext

class IsDbSyncedUseCase(
    private val allSyncApi: AllSyncApi,
    private val coreDispatchers: CoreDispatchers,
) {
    suspend fun execute(): Boolean = withContext(coreDispatchers.io) {
        allSyncApi.isSynced()
    }
}
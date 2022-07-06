package com.itrocket.union.container.domain

import com.example.union_sync_api.data.ManageSyncDataApi
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.withContext

class IsDbSyncedUseCase(
    private val manageSyncApi: ManageSyncDataApi,
    private val coreDispatchers: CoreDispatchers,
) {
    suspend fun execute(): Boolean = withContext(coreDispatchers.io) {
        manageSyncApi.isSynced()
    }
}
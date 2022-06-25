package com.itrocket.union.syncAll.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import kotlinx.coroutines.withContext

class SyncAllInteractor(
    private val repository: SyncAllRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun syncAll() = withContext(coreDispatchers.io) {
        repository.syncAll()
    }
}
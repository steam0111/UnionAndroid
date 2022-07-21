package com.itrocket.union.syncAll.domain.dependencies

interface SyncAllRepository {
    suspend fun syncAll()

    suspend fun clearAll()
}
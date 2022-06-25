package com.itrocket.union.syncAll.domain.dependencies

interface SyncAllRepository {
    suspend fun syncAll()
}
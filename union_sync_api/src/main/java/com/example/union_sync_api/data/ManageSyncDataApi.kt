package com.example.union_sync_api.data

interface ManageSyncDataApi {
    suspend fun clearAll()
    suspend fun isSynced(): Boolean
}
package com.example.union_sync_api.data

interface AllSyncApi {
    suspend fun syncAll()
    suspend fun isSynced(): Boolean
}
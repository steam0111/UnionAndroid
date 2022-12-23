package com.example.union_sync_api.data

interface NetworkSyncApi {

    suspend fun changeSyncFilesEnabled(enabled: Boolean)

    suspend fun getSyncFileEnabled(): Boolean
}
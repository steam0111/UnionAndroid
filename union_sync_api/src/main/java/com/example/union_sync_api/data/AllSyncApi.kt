package com.example.union_sync_api.data

import java.io.File

interface AllSyncApi {
    suspend fun syncAll(): String

    suspend fun syncFile(files: List<File>, syncId: String)

    suspend fun getLastSyncTime(): Long
}
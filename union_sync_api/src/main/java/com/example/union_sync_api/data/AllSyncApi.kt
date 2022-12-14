package com.example.union_sync_api.data

import java.io.File

interface AllSyncApi {
    suspend fun syncAll(files: List<File>)

    suspend fun getLastSyncTime(): Long
}
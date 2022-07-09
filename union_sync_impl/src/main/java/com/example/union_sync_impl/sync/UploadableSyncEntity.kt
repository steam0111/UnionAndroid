package com.example.union_sync_impl.sync

interface UploadableSyncEntity {
    suspend fun upload(syncId: String)
}
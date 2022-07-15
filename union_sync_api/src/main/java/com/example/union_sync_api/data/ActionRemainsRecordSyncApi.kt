package com.example.union_sync_api.data

import com.example.union_sync_api.entity.ActionRemainsRecordSyncEntity
import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity

interface ActionRemainsRecordSyncApi {

    fun getAll(actionId: String? = null): List<ActionRemainsRecordSyncEntity>

    suspend fun updateCounts(list: List<DocumentReserveCountSyncEntity>)
}
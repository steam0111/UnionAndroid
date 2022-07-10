package com.example.union_sync_api.data

import com.example.union_sync_api.entity.ActionRecordSyncEntity

interface ActionRecordSyncApi {

    fun getAll(actionId: String): List<ActionRecordSyncEntity>
}
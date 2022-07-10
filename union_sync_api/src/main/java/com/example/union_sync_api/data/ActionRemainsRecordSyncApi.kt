package com.example.union_sync_api.data

import com.example.union_sync_api.entity.ActionRemainsRecordSyncEntity

interface ActionRemainsRecordSyncApi {

    fun getAll(actionId: String? = null): List<ActionRemainsRecordSyncEntity>
}
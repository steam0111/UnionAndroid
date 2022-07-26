package com.example.union_sync_api.data

import com.example.union_sync_api.entity.ActionBaseSyncEntity

interface ActionBaseSyncApi {
    suspend fun getActionBases(
        textQuery: String? = null
    ): List<ActionBaseSyncEntity>
}
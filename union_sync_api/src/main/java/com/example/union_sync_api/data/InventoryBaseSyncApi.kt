package com.example.union_sync_api.data

import com.example.union_sync_api.entity.InventoryBaseSyncEntity

interface InventoryBaseSyncApi {
    suspend fun getAll(textQuery: String? = null): List<InventoryBaseSyncEntity>
}
package com.example.union_sync_api.data

import com.example.union_sync_api.entity.InventoryCheckerSyncEntity

interface InventoryCheckerSyncApi {
    suspend fun getCheckers(inventoryId: String): List<InventoryCheckerSyncEntity>
}

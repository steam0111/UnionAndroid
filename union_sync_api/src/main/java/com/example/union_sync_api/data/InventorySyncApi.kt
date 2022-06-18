package com.example.union_sync_api.data

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity

interface InventorySyncApi {
    suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity)
    suspend fun getInventories(): List<InventorySyncEntity>
}
package com.example.union_sync_api.data

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity

interface InventorySyncApi {
    suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): Long
    suspend fun getInventories(textQuery: String? = null): List<InventorySyncEntity>
    suspend fun getInventoryById(id: Long): InventorySyncEntity
    suspend fun updateInventory(inventoryUpdateSyncEntity: InventoryUpdateSyncEntity)
}
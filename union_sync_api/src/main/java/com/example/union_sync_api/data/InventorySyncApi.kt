package com.example.union_sync_api.data

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import kotlinx.coroutines.flow.Flow

interface InventorySyncApi {
    suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): Long
    suspend fun getInventories(): Flow<List<InventorySyncEntity>>
    suspend fun getInventoryById(id: Long): InventorySyncEntity
    suspend fun updateInventory(inventoryUpdateSyncEntity: InventoryUpdateSyncEntity)
}
package com.example.union_sync_api.data

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import kotlinx.coroutines.flow.Flow

interface InventorySyncApi {
    suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): String
    suspend fun getInventories(
        textQuery: String? = null,
        structuralId: String? = null,
        molId: String? = null
    ): Flow<List<InventorySyncEntity>>

    suspend fun getInventoriesCount(
        textQuery: String? = null,
        structuralId: String? = null,
        molId: String? = null
    ): Long

    suspend fun getInventoryById(id: String): InventorySyncEntity
    suspend fun updateInventory(inventoryUpdateSyncEntity: InventoryUpdateSyncEntity)
}
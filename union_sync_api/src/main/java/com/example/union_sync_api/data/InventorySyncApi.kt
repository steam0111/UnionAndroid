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
        molId: String? = null,
        inventoryBaseId: String? = null,
        offset: Long,
        limit: Long?,
        code: String?
    ): List<InventorySyncEntity>

    suspend fun getInventoriesCount(
        textQuery: String? = null,
        structuralId: String? = null,
        molId: String? = null,
        inventoryBaseId: String? = null,
        code: String? = null
    ): Long

    suspend fun getInventoryById(id: String, isAccountingObjectLoad: Boolean): InventorySyncEntity
    suspend fun updateInventory(inventoryUpdateSyncEntity: InventoryUpdateSyncEntity)
    suspend fun getInventoriesCodes(number: String): List<String>
}
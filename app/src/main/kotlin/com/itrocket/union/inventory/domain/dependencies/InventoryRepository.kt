package com.itrocket.union.inventory.domain.dependencies

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): String
    suspend fun updateInventory(inventoryUpdateSyncEntity: InventoryUpdateSyncEntity)
    suspend fun getInventoryById(id: String): InventoryCreateDomain
    suspend fun getInventories(
        textQuery: String? = null,
        params: List<ParamDomain>?,
        offset: Long,
        limit: Long?
    ): List<InventoryCreateDomain>

    suspend fun getInventoriesCount(
        textQuery: String? = null,
        params: List<ParamDomain>?
    ): Long
}
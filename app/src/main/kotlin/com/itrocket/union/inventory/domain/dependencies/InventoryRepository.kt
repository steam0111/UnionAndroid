package com.itrocket.union.inventory.domain.dependencies

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain

interface InventoryRepository {
    suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): Long
    suspend fun updateInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity)
    suspend fun getInventoryById(id: Long): InventoryCreateDomain
    suspend fun getInventories(): List<InventoryCreateDomain>
}
package com.example.union_sync_impl.data

import com.example.union_sync_api.data.InventorySyncApi
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_impl.dao.InventoryDao
import com.example.union_sync_impl.data.mapper.toInventoryDb
import com.example.union_sync_impl.data.mapper.toInventorySyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity

class InventorySyncApiImpl(private val inventoryDao: InventoryDao) : InventorySyncApi {
    override suspend fun createInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity): Long {
        return inventoryDao.insert(inventoryCreateSyncEntity.toInventoryDb())
    }

    override suspend fun getInventories(): List<InventorySyncEntity> {
        return inventoryDao.getAll().map {
            it.inventoryDb.toInventorySyncEntity(
                organizationSyncEntity = requireNotNull(it.organizationDb).toSyncEntity(),
                mol = requireNotNull(it.employeeDb).toSyncEntity()
            )
        }
    }

    override suspend fun getInventoryById(id: Long): InventorySyncEntity {
        val fullInventory = inventoryDao.getInventoryById(id)
        return fullInventory.inventoryDb.toInventorySyncEntity(
            organizationSyncEntity = requireNotNull(fullInventory.organizationDb).toSyncEntity(),
            mol = requireNotNull(fullInventory.employeeDb).toSyncEntity()
        )
    }

    override suspend fun updateInventory(inventoryCreateSyncEntity: InventoryCreateSyncEntity) {
        inventoryDao.update(inventoryCreateSyncEntity.toInventoryDb())
    }
}
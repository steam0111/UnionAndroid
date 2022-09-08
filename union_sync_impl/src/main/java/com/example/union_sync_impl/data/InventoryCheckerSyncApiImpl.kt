package com.example.union_sync_impl.data

import com.example.union_sync_api.data.InventoryCheckerSyncApi
import com.example.union_sync_api.entity.InventoryCheckerSyncEntity
import com.example.union_sync_impl.dao.InventoryCheckerDao
import com.example.union_sync_impl.data.mapper.toSyncEntity

class InventoryCheckerSyncApiImpl(
    private val checkerDao: InventoryCheckerDao
) : InventoryCheckerSyncApi {
    override suspend fun getCheckers(inventoryId: String): List<InventoryCheckerSyncEntity> {
        return checkerDao.getAll(inventoryId).map { it.toSyncEntity() }
    }
}
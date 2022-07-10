package com.example.union_sync_impl.data

import com.example.union_sync_api.data.InventoryRecordSyncApi
import com.example.union_sync_api.entity.InventoryRecordSyncEntity
import com.example.union_sync_impl.dao.InventoryRecordDao
import com.example.union_sync_impl.dao.sqlInventoryRecordQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class InventoryRecordSyncApiImpl(private val inventoryRecordDao: InventoryRecordDao) :
    InventoryRecordSyncApi {

    override fun getAll(inventoryId: String?): List<InventoryRecordSyncEntity> {
        return inventoryRecordDao.getAll(sqlInventoryRecordQuery(inventoryId = inventoryId)).map {
            it.toSyncEntity()
        }
    }
}
package com.example.union_sync_impl.data

import com.example.union_sync_api.data.InventoryNomenclatureRecordSyncApi
import com.example.union_sync_api.entity.InventoryNomenclatureRecordSyncEntity
import com.example.union_sync_impl.dao.InventoryNomenclatureRecordDao
import com.example.union_sync_impl.dao.sqlInventoryNomenclatureRecordQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class InventoryNomenclatureRecordSyncApiImpl(private val inventoryNomenclatureRecordDao: InventoryNomenclatureRecordDao) : InventoryNomenclatureRecordSyncApi {
    override fun getAll(inventoryId: String?): List<InventoryNomenclatureRecordSyncEntity> {
        return inventoryNomenclatureRecordDao.getAll(sqlInventoryNomenclatureRecordQuery(inventoryId = inventoryId)).map {
            it.toSyncEntity()
        }
    }
}
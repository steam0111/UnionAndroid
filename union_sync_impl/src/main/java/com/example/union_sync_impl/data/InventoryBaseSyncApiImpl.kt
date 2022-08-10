package com.example.union_sync_impl.data

import com.example.union_sync_api.data.InventoryBaseSyncApi
import com.example.union_sync_api.entity.InventoryBaseSyncEntity
import com.example.union_sync_impl.dao.InventoryBaseDao
import com.example.union_sync_impl.dao.sqlInventoryBaseQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class InventoryBaseSyncApiImpl(private val dao: InventoryBaseDao) :
    InventoryBaseSyncApi {
    override suspend fun getAll(textQuery: String?): List<InventoryBaseSyncEntity> {
        return dao.getAll(sqlInventoryBaseQuery(textQuery = textQuery))
            .map { it.toSyncEntity() }
    }
}
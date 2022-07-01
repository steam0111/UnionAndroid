package com.example.union_sync_impl.data

import com.example.union_sync_api.data.ReceptionItemCategorySyncApi
import com.example.union_sync_api.entity.ReceptionItemCategorySyncEntity
import com.example.union_sync_impl.dao.ReceptionItemCategoryDao
import com.example.union_sync_impl.dao.sqlReceptionItemCategoryQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class ReceptionCategoryItemSyncApiImpl(private val receptionItemCategoryDao: ReceptionItemCategoryDao) :
    ReceptionItemCategorySyncApi {
    override suspend fun getAll(textQuery: String?): List<ReceptionItemCategorySyncEntity> {
        return receptionItemCategoryDao.getAll(sqlReceptionItemCategoryQuery(textQuery = textQuery))
            .map { it.toSyncEntity() }
    }
}
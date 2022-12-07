package com.example.union_sync_impl.data

import com.example.union_sync_api.data.LabelTypeSyncApi
import com.example.union_sync_api.entity.LabelTypeSyncEntity
import com.example.union_sync_impl.dao.LabelTypeDao
import com.example.union_sync_impl.dao.sqlLabelTypeQuery
import com.example.union_sync_impl.data.mapper.toLabelTypeSyncEntity

class LabelTypeSyncApiImpl(private val labelTypeDao: LabelTypeDao) : LabelTypeSyncApi {
    override suspend fun getLabelTypeById(id: String): LabelTypeSyncEntity {
        return labelTypeDao.getById(id).toLabelTypeSyncEntity()
    }

    override suspend fun getLabelTypes(
        textQuery: String?,
        offset: Long?,
        limit: Long?
    ): List<LabelTypeSyncEntity> {
        return labelTypeDao.getLabelTypes(
            sqlLabelTypeQuery(
                textQuery = textQuery,
                offset = offset,
                limit = limit
            )
        ).map { it.toLabelTypeSyncEntity() }
    }

}
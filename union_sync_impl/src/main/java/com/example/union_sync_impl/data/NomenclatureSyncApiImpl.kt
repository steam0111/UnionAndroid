package com.example.union_sync_impl.data

import com.example.union_sync_api.data.NomenclatureSyncApi
import com.example.union_sync_api.entity.NomenclatureDetailSyncEntity
import com.example.union_sync_api.entity.NomenclatureSyncEntity
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.sqlNomenclatureQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity

class NomenclatureSyncApiImpl(
    private val nomenclatureDao: NomenclatureDao
) : NomenclatureSyncApi {

    override suspend fun getNomenclatures(
        groupId: String?,
        textQuery: String?,
        offset: Long?,
        limit: Long?
    ): List<NomenclatureSyncEntity> {
        return nomenclatureDao.getAll(
            sqlNomenclatureQuery(
                groupId,
                textQuery,
                limit = limit,
                offset = offset
            )
        )
            .map { it.toSyncEntity() }
    }

    override suspend fun getNomenclaturesCount(groupId: String?, textQuery: String?): Long {
        return nomenclatureDao.getCount(
            sqlNomenclatureQuery(
                groupId,
                textQuery,
                isFilterCount = true
            )
        )
    }

    override suspend fun getNomenclatureDetail(id: String): NomenclatureDetailSyncEntity {
        return nomenclatureDao.getById(id).toDetailSyncEntity()
    }
}
package com.example.union_sync_impl.data

import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.dao.sqlNomenclatureGroupQuery
import com.example.union_sync_impl.data.mapper.toSyncEntity

class NomenclatureGroupSyncApiImpl(
    private val nomenclatureGroupDao: NomenclatureGroupDao
) : NomenclatureGroupSyncApi {

    override suspend fun getNomenclatureGroups(textQuery: String?): List<NomenclatureGroupSyncEntity> {
        val nomenclatureDb = nomenclatureGroupDao.getAll(sqlNomenclatureGroupQuery(textQuery = textQuery))
        return nomenclatureDb.map { it.toSyncEntity() }
    }

    override suspend fun getNomenclatureGroupDetail(id: String): NomenclatureGroupSyncEntity {
        return nomenclatureGroupDao.getById(id).toSyncEntity()
    }
}
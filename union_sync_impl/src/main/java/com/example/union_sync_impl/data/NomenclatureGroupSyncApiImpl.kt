package com.example.union_sync_impl.data

import com.example.union_sync_api.data.NomenclatureGroupSyncApi
import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.data.mapper.toNomenclatureGroupDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import org.openapitools.client.custom_api.NomenclatureGroupApi

class NomenclatureGroupSyncApiImpl(
    private val nomenclatureGroupApi: NomenclatureGroupApi,
    private val nomenclatureGroupDao: NomenclatureGroupDao
) : NomenclatureGroupSyncApi {

    override suspend fun getNomenclatureGroups(): List<NomenclatureGroupSyncEntity> {
        val nomenclatureDb = nomenclatureGroupDao.getAll()
        if (nomenclatureDb.isEmpty()) {
            val nomenclaturesNetwork =
                nomenclatureGroupApi.apiCatalogsNomenclatureGroupGet().list ?: return emptyList()
            nomenclatureGroupDao.insertAll(nomenclaturesNetwork.map { it.toNomenclatureGroupDb() })
            return nomenclatureGroupDao.getAll().map { it.toSyncEntity() }
        }
        return nomenclatureDb.map { it.toSyncEntity() }
    }
}
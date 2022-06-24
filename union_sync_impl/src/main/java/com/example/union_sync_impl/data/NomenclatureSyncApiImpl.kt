package com.example.union_sync_impl.data

import com.example.union_sync_api.data.NomenclatureSyncApi
import com.example.union_sync_api.entity.NomenclatureDetailSyncEntity
import com.example.union_sync_api.entity.NomenclatureSyncEntity
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity

class NomenclatureSyncApiImpl(
    private val nomenclatureDao: NomenclatureDao
) : NomenclatureSyncApi {
    override suspend fun getNomenclatures(): List<NomenclatureSyncEntity> {
        return nomenclatureDao.getAll().map { it.toSyncEntity() }
    }

    override suspend fun getNomenclatureDetail(id: String): NomenclatureDetailSyncEntity {
        return nomenclatureDao.getById(id).toDetailSyncEntity()
    }
}
package com.example.union_sync_impl.data

import com.example.union_sync_api.data.RegionSyncApi
import com.example.union_sync_api.entity.RegionDetailSyncEntity
import com.example.union_sync_api.entity.RegionSyncEntity
import com.example.union_sync_impl.dao.RegionDao
import com.example.union_sync_impl.dao.sqlRegionQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity

class RegionSyncApiImpl(
    private val regionDao: RegionDao
) : RegionSyncApi {
    override suspend fun getRegions(
        organizationId: String?,
        textQuery: String?
    ): List<RegionSyncEntity> {
        return regionDao.getAll(sqlRegionQuery(organizationId)).map { it.toSyncEntity() }
    }

    override suspend fun getRegionDetail(id: String): RegionDetailSyncEntity {
        return regionDao.getFullById(id).toDetailSyncEntity()
    }
}
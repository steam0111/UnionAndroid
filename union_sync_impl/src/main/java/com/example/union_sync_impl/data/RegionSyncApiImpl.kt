package com.example.union_sync_impl.data

import com.example.union_sync_api.data.RegionSyncApi
import com.example.union_sync_api.entity.RegionDetailSyncEntity
import com.example.union_sync_api.entity.RegionSyncEntity
import com.example.union_sync_impl.dao.RegionDao
import com.example.union_sync_impl.dao.sqlRegionQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toRegionDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegionSyncApiImpl(
    private val regionDao: RegionDao
) : RegionSyncApi {
    override suspend fun getRegions(
        organizationId: String?,
        textQuery: String?
    ): Flow<List<RegionSyncEntity>> {
        return flow {
            emit(regionDao.getAll(sqlRegionQuery(organizationId = organizationId, textQuery = textQuery)).map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getRegionDetail(id: String): RegionDetailSyncEntity {
        return regionDao.getFullById(id).toDetailSyncEntity()
    }
}
package com.example.union_sync_impl.data

import com.example.union_sync_api.data.RegionSyncApi
import com.example.union_sync_api.entity.RegionSyncEntity
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.dao.RegionDao
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toRegionDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.openapitools.client.custom_api.RegionApi

class RegionSyncApiImpl(
    private val organizationDao: OrganizationDao,
    private val regionDao: RegionDao,
    private val regionApi: RegionApi
) : RegionSyncApi {
    override suspend fun getRegions(): Flow<List<RegionSyncEntity>> {
        return flow {
            emit(regionDao.getAll().map { it.toSyncEntity() })
            val regionNetwork = regionApi.apiCatalogRegionGet().list.orEmpty()
            regionNetwork.mapNotNull { it?.extendedOrganization?.toOrganizationDb() }
                .let { dbOrganization -> organizationDao.insertAll(dbOrganization) }

            regionNetwork.mapNotNull { it?.toRegionDb() }.let { dbRegion ->
                regionDao.insertAll(dbRegion)
            }
            emit(regionDao.getAll().map { it.toSyncEntity() })
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }
}
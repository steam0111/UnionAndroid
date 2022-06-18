package com.itrocket.union.regions.data

import com.example.union_sync_api.data.RegionSyncApi
import com.itrocket.union.regions.data.mapper.map
import com.itrocket.union.regions.domain.dependencies.RegionRepository
import com.itrocket.union.regions.domain.entity.RegionDomain
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RegionRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val regionSyncApi: RegionSyncApi
) : RegionRepository {

    override suspend fun getRegions(): Flow<List<RegionDomain>> {
        return withContext(coreDispatchers.io) {
            regionSyncApi.getRegions().map { it.map() }
        }
    }
}
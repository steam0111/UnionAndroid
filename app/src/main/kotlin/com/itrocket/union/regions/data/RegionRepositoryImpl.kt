package com.itrocket.union.regions.data

import com.example.union_sync_api.data.RegionSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.regions.data.mapper.map
import com.itrocket.union.regions.domain.dependencies.RegionRepository
import com.itrocket.union.regions.domain.entity.RegionDomain
import kotlinx.coroutines.withContext

class RegionRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val regionSyncApi: RegionSyncApi
) : RegionRepository {

    override suspend fun getRegions(textQuery: String?): List<RegionDomain> {
        return withContext(coreDispatchers.io) {
            regionSyncApi.getRegions(textQuery = textQuery).map()
        }
    }
}
package com.itrocket.union.regions.domain.dependencies

import com.itrocket.union.regions.domain.entity.RegionDomain
import kotlinx.coroutines.flow.Flow

interface RegionRepository {

    suspend fun getRegions(): Flow<List<RegionDomain>>
}
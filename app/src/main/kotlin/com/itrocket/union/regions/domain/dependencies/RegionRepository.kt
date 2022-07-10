package com.itrocket.union.regions.domain.dependencies

import com.itrocket.union.regions.domain.entity.RegionDomain

interface RegionRepository {

    suspend fun getRegions(textQuery: String? = null): List<RegionDomain>

    suspend fun getRegionsCount(textQuery: String? = null): Long
}
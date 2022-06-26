package com.itrocket.union.regions.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.regions.domain.dependencies.RegionRepository
import com.itrocket.union.regions.domain.entity.RegionDomain
import kotlinx.coroutines.withContext

class RegionInteractor(
    private val repository: RegionRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getRegions(searchQuery: String = ""): List<RegionDomain> = withContext(coreDispatchers.io) {
        repository.getRegions(searchQuery)
    }
}
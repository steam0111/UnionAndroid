package com.itrocket.union.regions.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.regions.domain.dependencies.RegionRepository
import com.itrocket.core.base.CoreDispatchers

class RegionInteractor(
    private val repository: RegionRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getRegions(searchQuery: String = "") = withContext(coreDispatchers.io) {
        repository.getRegions()
    }
}
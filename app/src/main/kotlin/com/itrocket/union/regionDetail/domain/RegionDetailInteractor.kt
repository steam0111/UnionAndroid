package com.itrocket.union.regionDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.regionDetail.domain.dependencies.RegionDetailRepository
import com.itrocket.union.regionDetail.domain.entity.RegionDetailDomain
import kotlinx.coroutines.withContext

class RegionDetailInteractor(
    private val repository: RegionDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getRegionDetail(id: String): RegionDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getRegionDetail(id)
        }
}
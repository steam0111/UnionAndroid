package com.itrocket.union.regionDetail.data

import com.example.union_sync_api.data.RegionSyncApi
import com.itrocket.union.regionDetail.data.mapper.toRegionDetailDomain
import com.itrocket.union.regionDetail.domain.dependencies.RegionDetailRepository
import com.itrocket.union.regionDetail.domain.entity.RegionDetailDomain

class RegionDetailRepositoryImpl(
    private val syncApi: RegionSyncApi
) : RegionDetailRepository {

    override suspend fun getRegionDetail(id: String): RegionDetailDomain {
        return syncApi.getRegionDetail(id).toRegionDetailDomain()
    }
}

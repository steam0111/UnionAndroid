package com.itrocket.union.regionDetail.domain.dependencies

import com.itrocket.union.regionDetail.domain.entity.RegionDetailDomain

interface RegionDetailRepository {
    suspend fun getRegionDetail(id: String): RegionDetailDomain
}
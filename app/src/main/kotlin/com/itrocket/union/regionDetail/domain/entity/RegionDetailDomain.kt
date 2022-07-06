package com.itrocket.union.regionDetail.domain.entity

import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

data class RegionDetailDomain(
    val listInfo: List<ObjectInfoDomain> = emptyList()
)
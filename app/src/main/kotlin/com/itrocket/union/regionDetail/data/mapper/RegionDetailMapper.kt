package com.itrocket.union.regionDetail.data.mapper

import com.example.union_sync_api.entity.RegionDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.regionDetail.domain.entity.RegionDetailDomain

fun RegionDetailSyncEntity.toRegionDetailDomain(): RegionDetailDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()
    region.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_name, it))
    }
    region.code?.let {
        listInfo.add(ObjectInfoDomain(R.string.region_code, it))
    }
    return RegionDetailDomain(listInfo)
}
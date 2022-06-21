package com.itrocket.union.location.data.mapper

import com.example.union_sync_api.entity.LocationSyncEntity
import com.itrocket.union.location.domain.entity.LocationDomain

fun LocationSyncEntity.toLocationDomain(): LocationDomain {
    return LocationDomain(
        value = name,
        type = locationType,
        locationTypeId = locationTypeId,
        id = id
    )
}
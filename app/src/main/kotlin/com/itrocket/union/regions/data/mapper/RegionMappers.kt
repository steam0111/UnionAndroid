package com.itrocket.union.regions.data.mapper

import com.example.union_sync_api.entity.RegionSyncEntity
import com.itrocket.union.regions.domain.entity.RegionDomain

fun List<RegionSyncEntity>.map(): List<RegionDomain> = map {
    RegionDomain(
        id = it.id,
        catalogItemName = it.catalogItemName,
        name = it.name.orEmpty(),
        code = it.code.orEmpty()
    )
}
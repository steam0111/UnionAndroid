package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import org.openapitools.client.models.CustomLocationDto
import org.openapitools.client.models.CustomLocationsTypeDto

fun CustomLocationDto.toLocationDb(): LocationDb {
    return LocationDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId,
        name = name,
        locationTypeId = locationTypeId
    )
}

fun CustomLocationsTypeDto.toLocationTypeDb(): LocationTypeDb {
    return LocationTypeDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId,
        name = name
    )
}

fun LocationDb.toLocationSyncEntity(
    locationTypeId: String,
    locationType: String
): LocationSyncEntity {
    return LocationSyncEntity(
        id = id,
        name = name,
        locationType = locationType,
        locationTypeId = locationTypeId
    )
}
package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import org.openapitools.client.models.CustomLocationDto
import org.openapitools.client.models.CustomLocationsTypeDto
import org.openapitools.client.models.LocationDtoV2
import org.openapitools.client.models.LocationsTypeDtoV2

fun CustomLocationDto.toLocationDb(): LocationDb {
    return LocationDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId,
        name = name,
        locationTypeId = locationTypeId,
        updateDate = System.currentTimeMillis()
    )
}

fun LocationDtoV2.toLocationDb(): LocationDb {
    return LocationDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId,
        name = name.orEmpty(),
        locationTypeId = locationTypeId,
        updateDate = System.currentTimeMillis()
    )
}

fun CustomLocationsTypeDto.toLocationTypeDb(): LocationTypeDb {
    return LocationTypeDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId,
        name = name,
        updateDate = System.currentTimeMillis()
    )
}

fun LocationsTypeDtoV2.toLocationTypeDb(): LocationTypeDb {
    return LocationTypeDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId,
        name = name,
        updateDate = System.currentTimeMillis()
    )
}

fun LocationDb.toLocationShortSyncEntity() = LocationShortSyncEntity(id = id, name = name)

fun LocationDb.toLocationSyncEntity(locationTypeDb: LocationTypeDb): LocationSyncEntity {
    return LocationSyncEntity(
        id = id,
        name = name,
        locationType = locationTypeDb.name,
        locationTypeId = locationTypeDb.id
    )
}
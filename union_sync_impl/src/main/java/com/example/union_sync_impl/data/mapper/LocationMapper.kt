package com.example.union_sync_impl.data.mapper

import com.example.union_sync_impl.entity.location.LocationDb
import org.openapitools.client.models.Location
import org.openapitools.client.models.LocationDto

fun Location.toLocationDb(): LocationDb {
    return LocationDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId
    )
}
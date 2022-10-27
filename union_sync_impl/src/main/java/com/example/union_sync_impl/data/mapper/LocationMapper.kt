package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.LocationTypeSyncEntity
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationPathDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.LocationDtoV2
import org.openapitools.client.models.LocationPathDto
import org.openapitools.client.models.LocationsTypeDtoV2

fun LocationDtoV2.toLocationDb(): LocationDb {
    return LocationDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId,
        name = name,
        locationTypeId = locationTypeId,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userUpdated = userUpdated,
        userInserted = userInserted,
        cancel = deleted
    )
}

fun LocationsTypeDtoV2.toLocationTypeDb(): LocationTypeDb {
    return LocationTypeDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        parentId = parentId,
        name = name,
        cancel = false
    )
}

fun LocationDb.toLocationShortSyncEntity() = LocationShortSyncEntity(id = id, name = name)

fun LocationDb.toLocationSyncEntity(locationTypeDb: LocationTypeDb?): LocationSyncEntity {
    return LocationSyncEntity(
        id = id,
        name = name,
        locationType = locationTypeDb?.name.orEmpty(),
        locationTypeId = locationTypeDb?.id.orEmpty()
    )
}

fun LocationTypeDb.toSyncEntity() = LocationTypeSyncEntity(
    id, name
)

fun LocationPathDto.toLocationPathDb(): LocationPathDb {
    val ancestor = if (descendantId == ancestorId) null else ancestorId
    return LocationPathDb(
        descendantLocationId = descendantId,
        ancestorLocationId = ancestor
    )
}
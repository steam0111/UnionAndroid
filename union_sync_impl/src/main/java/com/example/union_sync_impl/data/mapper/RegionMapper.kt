package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.RegionDetailSyncEntity
import com.example.union_sync_api.entity.RegionSyncEntity
import com.example.union_sync_impl.entity.FullRegionDb
import com.example.union_sync_impl.entity.RegionDb
import org.openapitools.client.models.Region
import org.openapitools.client.models.RegionDtoV2

fun RegionDtoV2.toRegionDb(): RegionDb {
    return RegionDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        organizationId = organizationId,
        name = name,
        code = code,
        updateDate = System.currentTimeMillis()
    )
}

fun RegionDb.toSyncEntity(): RegionSyncEntity {
    return RegionSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        organizationId = organizationId,
        name = name,
        code = code
    )
}

fun FullRegionDb.toDetailSyncEntity() = RegionDetailSyncEntity(
    region = regionDb.toSyncEntity()
)
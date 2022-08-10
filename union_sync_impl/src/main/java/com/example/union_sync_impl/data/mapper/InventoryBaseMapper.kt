package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.InventoryBaseSyncEntity
import com.example.union_sync_impl.entity.InventoryBaseDb
import org.openapitools.client.models.EnumDtoV2
import org.openapitools.client.models.InventoryBaseDtoV2

fun EnumDtoV2.toInventoryBaseDb(): InventoryBaseDb {
    return InventoryBaseDb(
        id = id,
        name = name.orEmpty()
    )
}

fun InventoryBaseDb.toSyncEntity(): InventoryBaseSyncEntity {
    return InventoryBaseSyncEntity(
        id = id,
        name = name
    )
}
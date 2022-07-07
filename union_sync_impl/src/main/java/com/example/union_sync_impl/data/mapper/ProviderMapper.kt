package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ProviderSyncEntity
import com.example.union_sync_impl.entity.ProviderDb
import org.openapitools.client.models.CounterpartyDto
import org.openapitools.client.models.CounterpartyDtoV2

fun CounterpartyDto.toProviderDb(): ProviderDb {
    return ProviderDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name,
        updateDate = System.currentTimeMillis()
    )
}

fun CounterpartyDtoV2.toProviderDb(): ProviderDb {
    return ProviderDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name
    )
}

fun ProviderDb.toSyncEntity(): ProviderSyncEntity {
    return ProviderSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        name = name
    )
}
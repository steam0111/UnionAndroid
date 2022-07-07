package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ProducerSyncEntity
import com.example.union_sync_impl.entity.ProducerDb
import org.openapitools.client.models.Producer
import org.openapitools.client.models.ProducerDtoV2

fun ProducerDtoV2.toProducerDb(): ProducerDb {
    return ProducerDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name,
        code = code,
        updateDate = System.currentTimeMillis()
    )
}

fun ProducerDb.toSyncEntity(): ProducerSyncEntity {
    return ProducerSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        name = name,
        code = code
    )
}
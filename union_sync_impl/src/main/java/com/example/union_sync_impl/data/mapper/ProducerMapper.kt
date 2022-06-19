package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ProducerSyncEntity
import com.example.union_sync_impl.entity.ProducerDb
import org.openapitools.client.models.Producer

fun Producer.toProducerDb(): ProducerDb {
    return ProducerDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name,
        code = code
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
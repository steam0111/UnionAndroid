package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ProducerSyncEntity
import com.example.union_sync_impl.entity.ProducerDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.ProducerDtoV2

fun ProducerDtoV2.toProducerDb(): ProducerDb {
    return ProducerDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name,
        code = code,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userUpdated = userUpdated,
        userInserted = userInserted
    )
}

fun ProducerDb.toSyncEntity(): ProducerSyncEntity {
    return ProducerSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        name = name,
        code = code,
        userInserted = userInserted,
        userUpdated = userUpdated,
        dateInsert = insertDate,
        updateDate = updateDate
    )
}
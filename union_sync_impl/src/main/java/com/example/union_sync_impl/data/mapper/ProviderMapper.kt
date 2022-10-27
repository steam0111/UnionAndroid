package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ProviderSyncEntity
import com.example.union_sync_impl.entity.ProviderDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.CounterpartyDto
import org.openapitools.client.models.CounterpartyDtoV2

fun CounterpartyDtoV2.toProviderDb(): ProviderDb {
    return ProviderDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userUpdated = userUpdated,
        userInserted = userInserted,
        cancel = deleted
    )
}

fun ProviderDb.toSyncEntity(): ProviderSyncEntity {
    return ProviderSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        name = name
    )
}
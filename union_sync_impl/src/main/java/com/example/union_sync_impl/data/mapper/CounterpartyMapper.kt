package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.example.union_sync_impl.entity.CounterpartyDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.CounterpartyDtoV2

fun CounterpartyDtoV2.toCounterpartyDb(): CounterpartyDb {
    return CounterpartyDb(
        catalogItemName = catalogItemName.orEmpty(),
        name = name.orEmpty(),
        id = id,
        actualAddress = actualAddress,
        legalAddress = legalAddress,
        kpp = kpp,
        inn = inn,
        code = code,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userUpdated = userUpdated,
        userInserted = userInserted
    )
}

fun CounterpartyDb.toSyncEntity(): CounterpartySyncEntity {
    return CounterpartySyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        name = name,
        actualAddress = actualAddress,
        legalAddress = legalAddress,
        kpp = kpp,
        inn = inn,
        code = code,
        userInserted = userInserted,
        userUpdated = userUpdated,
        dateInsert = insertDate,
        updateDate = updateDate
    )
}
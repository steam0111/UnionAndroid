package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.example.union_sync_impl.entity.CounterpartyDb
import org.openapitools.client.models.Counterparty

fun Counterparty.toCounterpartyDb(): CounterpartyDb {
    return CounterpartyDb(
        catalogItemName = catalogItemName.orEmpty(),
        name = name.orEmpty(),
        id = id,
        actualAddress = actualAddress,
        legalAddress = legalAddress,
        kpp = kpp,
        inn = inn
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
        inn = inn
    )
}
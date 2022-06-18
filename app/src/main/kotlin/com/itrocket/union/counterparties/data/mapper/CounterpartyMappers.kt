package com.itrocket.union.counterparties.data.mapper

import com.example.union_sync_api.entity.CounterpartySyncEntity
import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain

fun List<CounterpartySyncEntity>.map(): List<CounterpartyDomain> = map {
    CounterpartyDomain(
        catalogItemName = it.catalogItemName,
        name = it.name.orEmpty(),
        id = it.id,
        actualAddress = it.actualAddress.orEmpty(),
        legalAddress = it.legalAddress.orEmpty(),
        kpp = it.kpp.orEmpty(),
        inn = it.inn.orEmpty()
    )
}
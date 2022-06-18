package com.itrocket.union.producer.data.mapper

import com.example.union_sync_api.entity.ProducerSyncEntity
import com.itrocket.union.producer.domain.entity.ProducerDomain

fun List<ProducerSyncEntity>.map(): List<ProducerDomain> = map {
    ProducerDomain(
        id = it.id,
        catalogItemName = it.catalogItemName,
        name = it.name.orEmpty(),
        code = it.code.orEmpty()
    )
}
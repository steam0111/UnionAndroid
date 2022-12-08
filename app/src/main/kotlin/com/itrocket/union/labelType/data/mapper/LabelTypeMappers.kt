package com.itrocket.union.labelType.data.mapper

import com.example.union_sync_api.entity.LabelTypeSyncEntity
import com.itrocket.union.labelType.domain.entity.LabelTypeDomain

fun List<LabelTypeSyncEntity>.map(): List<LabelTypeDomain> = map {
    it.map()
}

fun LabelTypeSyncEntity.map() = LabelTypeDomain(
    id = id,
    catalogItemName = catalogItemName,
    code = code.orEmpty(),
    description = description,
    name = name.orEmpty()
)
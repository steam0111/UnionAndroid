package com.itrocket.union.structural.data.mapper

import com.example.union_sync_api.entity.StructuralSyncEntity
import com.itrocket.union.structural.domain.entity.StructuralDomain

fun List<StructuralSyncEntity>.map(): List<StructuralDomain> = map {
    it.toStructuralDomain()
}

fun StructuralSyncEntity.toStructuralDomain() = StructuralDomain(
    id = id,
    value = name
)
package com.itrocket.union.equipmentTypes.data.mapper

import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain

fun List<EquipmentTypeSyncEntity>.map(): List<EquipmentTypesDomain> = map {
    EquipmentTypesDomain(
        id = it.id,
        catalogItemName = it.catalogItemName,
        name = it.name.orEmpty(),
        code = it.code.orEmpty()
    )
}
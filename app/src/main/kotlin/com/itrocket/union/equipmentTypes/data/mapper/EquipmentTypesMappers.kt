package com.itrocket.union.equipmentTypes.data.mapper

import com.example.union_sync_api.entity.EquipmentTypesSyncEntity
import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain

fun List<EquipmentTypesSyncEntity>.map(): List<EquipmentTypesDomain> = map {
    EquipmentTypesDomain(
        id = it.id,
        catalogItemName = it.catalogItemName,
        name = it.name,
        code = it.code
    )
}
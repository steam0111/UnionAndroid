package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.EquipmentTypesSyncEntity
import com.example.union_sync_impl.entity.EquipmentTypesDb
import org.openapitools.client.models.EquipmentTypeDto

fun EquipmentTypeDto.toEquipmentTypeDb(): EquipmentTypesDb {
    return EquipmentTypesDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name.orEmpty(),
        code = code.orEmpty()
    )
}

fun EquipmentTypesDb.toSyncEntity(): EquipmentTypesSyncEntity {
    return EquipmentTypesSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        name = name,
        code = code
    )
}
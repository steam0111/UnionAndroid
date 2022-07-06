package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import com.example.union_sync_impl.entity.EquipmentTypesDb
import org.openapitools.client.models.EquipmentTypeDto

fun EquipmentTypeDto.toEquipmentTypeDb(): EquipmentTypesDb {
    return EquipmentTypesDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name,
        code = code
    )
}

fun EquipmentTypesDb.toSyncEntity(): EquipmentTypeSyncEntity {
    return EquipmentTypeSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        name = name,
        code = code
    )
}
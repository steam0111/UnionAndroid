package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.EquipmentTypeSyncEntity
import com.example.union_sync_impl.entity.EquipmentTypesDb
import org.openapitools.client.models.EquipmentTypeDto
import org.openapitools.client.models.EquipmentTypeDtoV2

fun EquipmentTypeDtoV2.toEquipmentTypeDb(): EquipmentTypesDb {
    return EquipmentTypesDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name,
        code = code,
        updateDate = System.currentTimeMillis()
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
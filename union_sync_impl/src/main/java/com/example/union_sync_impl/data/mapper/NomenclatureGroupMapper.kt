package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity
import com.example.union_sync_impl.entity.NomenclatureGroupDb
import org.openapitools.client.models.NomenclatureGroupDto

fun NomenclatureGroupDto.toNomenclatureDb(): NomenclatureGroupDb {
    return NomenclatureGroupDb(
        catalogItemName = catalogItemName ?: ""
    )
}

fun NomenclatureGroupDb.toSyncEntity(): NomenclatureGroupSyncEntity {
    return NomenclatureGroupSyncEntity(
        id = id,
        name = catalogItemName
    )
}
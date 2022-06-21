package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity
import com.example.union_sync_impl.entity.NomenclatureGroupDb
import org.openapitools.client.models.NomenclatureGroupDto

fun NomenclatureGroupDto.toNomenclatureGroupDb(): NomenclatureGroupDb {
    return NomenclatureGroupDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name.orEmpty()
    )
}

fun NomenclatureGroupDb.toSyncEntity(): NomenclatureGroupSyncEntity {
    return NomenclatureGroupSyncEntity(
        id = id,
        name = name,
        catalogItemName = catalogItemName
    )
}
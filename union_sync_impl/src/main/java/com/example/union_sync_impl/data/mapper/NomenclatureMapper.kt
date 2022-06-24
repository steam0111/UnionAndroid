package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.NomenclatureDetailSyncEntity
import com.example.union_sync_api.entity.NomenclatureSyncEntity
import com.example.union_sync_impl.entity.FullNomenclatureDb
import com.example.union_sync_impl.entity.NomenclatureDb
import org.openapitools.client.models.Nomenclature

fun Nomenclature.toNomenclatureDb(): NomenclatureDb {
    return NomenclatureDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        nomenclatureGroupId = nomenclatureGroupId.orEmpty(),
        number = code.orEmpty(),
        name = name.orEmpty()
    )
}

fun NomenclatureDb.toSyncEntity() = NomenclatureSyncEntity(
    id = id,
    code = number,
    name = name,
    nomenclatureGroupId = nomenclatureGroupId,
    catalogItemName = catalogItemName
)

fun FullNomenclatureDb.toDetailSyncEntity() = NomenclatureDetailSyncEntity(
    nomenclature = nomenclatureDb.toSyncEntity(),
    nomenclatureGroup = nomenclatureGroupDb?.toSyncEntity()
)
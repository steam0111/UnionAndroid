package com.example.union_sync_impl.data.mapper

import com.example.union_sync_impl.entity.NomenclatureDb
import org.openapitools.client.models.Nomenclature
import org.openapitools.client.models.NomenclatureDto

fun Nomenclature.toNomenclatureDb(): NomenclatureDb {
    return NomenclatureDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        nomenclatureGroupId = nomenclatureGroupId.orEmpty(),
        number = code.orEmpty()
    )
}
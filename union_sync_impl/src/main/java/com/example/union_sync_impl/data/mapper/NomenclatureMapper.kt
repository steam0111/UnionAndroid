package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.NomenclatureDetailSyncEntity
import com.example.union_sync_api.entity.NomenclatureSyncEntity
import com.example.union_sync_impl.entity.FullNomenclatureDb
import com.example.union_sync_impl.entity.NomenclatureDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.NomenclatureDtoV2

fun NomenclatureDtoV2.toNomenclatureDb(): NomenclatureDb {
    return NomenclatureDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        nomenclatureGroupId = nomenclatureGroupId,
        number = code.orEmpty(),
        name = name.orEmpty(),
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userUpdated = userUpdated,
        userInserted = userInserted,
        cancel = deleted,
        barcode = barcodeValue
    )
}

fun NomenclatureDb.toSyncEntity() = NomenclatureSyncEntity(
    id = id,
    code = number,
    name = name,
    nomenclatureGroupId = nomenclatureGroupId,
    catalogItemName = catalogItemName,
    userInserted = userInserted,
    userUpdated = userUpdated,
    dateInsert = insertDate,
    updateDate = updateDate,
    barcode = barcode
)

fun FullNomenclatureDb.toDetailSyncEntity() = NomenclatureDetailSyncEntity(
    nomenclature = nomenclatureDb.toSyncEntity(),
    nomenclatureGroup = nomenclatureGroupDb?.toSyncEntity()
)
package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.NomenclatureGroupSyncEntity
import com.example.union_sync_impl.entity.NomenclatureGroupDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.NomenclatureGroupDtoV2

fun NomenclatureGroupDtoV2.toNomenclatureGroupDb(): NomenclatureGroupDb {
    return NomenclatureGroupDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        name = name.orEmpty(),
        code = code,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userUpdated = userUpdated,
        userInserted = userInserted
    )
}

fun NomenclatureGroupDb.toSyncEntity(): NomenclatureGroupSyncEntity {
    return NomenclatureGroupSyncEntity(
        id = id,
        name = name,
        code = code,
        catalogItemName = catalogItemName,
        userInserted = userInserted,
        userUpdated = userUpdated,
        dateInsert = insertDate,
        updateDate = updateDate
    )
}
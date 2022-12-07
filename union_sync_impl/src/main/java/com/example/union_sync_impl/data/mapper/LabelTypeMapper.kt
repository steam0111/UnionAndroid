package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.LabelTypeSyncEntity
import com.example.union_sync_impl.entity.LabelTypeDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import org.openapitools.client.models.LabelTypeDtoV2

fun LabelTypeDtoV2.toLabelTypeDb() = LabelTypeDb(
    id = id,
    catalogItemName = catalogItemName.orEmpty(),
    name = name,
    code = code,
    description = description,
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    insertDate = getMillisDateFromServerFormat(dateInsert),
    userUpdated = userUpdated,
    userInserted = userInserted
)

fun LabelTypeDb.toLabelTypeSyncEntity() = LabelTypeSyncEntity(
    id = id,
    catalogItemName = catalogItemName,
    name = name,
    code = code,
    description = description,
    userInserted = userInserted,
    userUpdated = userUpdated,
    dateInsert = insertDate,
    updateDate = updateDate
)
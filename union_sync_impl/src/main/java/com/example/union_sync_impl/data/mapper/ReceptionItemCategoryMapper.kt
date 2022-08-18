package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ReceptionItemCategorySyncEntity
import com.example.union_sync_impl.entity.ReceptionItemCategoryDb
import org.openapitools.client.models.CustomReceptionItemCategoryDto
import org.openapitools.client.models.EnumDtoV2
import org.openapitools.client.models.ReceptionItemCategoryDtoV2

fun EnumDtoV2.toReceptionItemCategoryDb(): ReceptionItemCategoryDb {
    return ReceptionItemCategoryDb(
        id = id,
        name = name.orEmpty()
    )
}

fun ReceptionItemCategoryDb.toSyncEntity(): ReceptionItemCategorySyncEntity {
    return ReceptionItemCategorySyncEntity(
        id = id,
        name = name
    )
}
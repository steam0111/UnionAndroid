package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ReceptionItemCategorySyncEntity
import com.example.union_sync_impl.entity.ReceptionItemCategoryDb
import org.openapitools.client.models.CustomReceptionItemCategoryDto

fun CustomReceptionItemCategoryDto.toReceptionItemCategoryDb(): ReceptionItemCategoryDb {
    return ReceptionItemCategoryDb(
        id = id,
        name = name
    )
}

fun ReceptionItemCategoryDb.toSyncEntity(): ReceptionItemCategorySyncEntity {
    return ReceptionItemCategorySyncEntity(
        id = id,
        name = name
    )
}
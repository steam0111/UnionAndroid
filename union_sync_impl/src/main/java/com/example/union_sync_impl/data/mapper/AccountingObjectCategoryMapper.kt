package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectCategorySyncEntity
import com.example.union_sync_impl.entity.AccountingObjectCategoryDb
import org.openapitools.client.models.EnumDtoV2

fun EnumDtoV2.toAccountingObjectCategoryDb(): AccountingObjectCategoryDb {
    return AccountingObjectCategoryDb(
        id = id,
        name = name.orEmpty()
    )
}

fun AccountingObjectCategoryDb.toSyncEntity(): AccountingObjectCategorySyncEntity {
    return AccountingObjectCategorySyncEntity(
        id = id,
        name = name
    )
}
package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectUnionImageSyncEntity
import com.example.union_sync_impl.entity.AccountingObjectUnionImageDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.AccountingObjectUnionImageDtoV2

fun AccountingObjectUnionImageDtoV2.toDb() = AccountingObjectUnionImageDb(
    id = accountingObjectId,
    cancel = deleted,
    accountingObjectId = accountingObjectId,
    isMainImage = isMainImage,
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    insertDate = getMillisDateFromServerFormat(dateInsert),
    userInserted = userInserted,
    userUpdated = userUpdated,
)

fun AccountingObjectUnionImageDb.toDto() = AccountingObjectUnionImageDtoV2(
    unionImageId = id,
    deleted = cancel ?: false,
    accountingObjectId = accountingObjectId,
    isMainImage = isMainImage,
    dateUpdate = getStringDateFromMillis(updateDate),
    dateInsert = getStringDateFromMillis(insertDate),
    userInserted = userInserted,
    userUpdated = userUpdated,
)

fun AccountingObjectUnionImageDb.toSyncEntity() = AccountingObjectUnionImageSyncEntity(
    unionImageId = id,
    accountingObjectId = accountingObjectId,
    isMainImage = isMainImage,
    userInserted = userInserted
)

fun AccountingObjectUnionImageSyncEntity.toDb() = AccountingObjectUnionImageDb(
    id = unionImageId,
    accountingObjectId = accountingObjectId,
    isMainImage = isMainImage,
    updateDate = System.currentTimeMillis(),
    insertDate = System.currentTimeMillis(),
    userInserted = userInserted,
    userUpdated = userInserted,
)
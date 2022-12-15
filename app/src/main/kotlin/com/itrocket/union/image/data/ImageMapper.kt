package com.itrocket.union.image.data

import com.example.union_sync_api.entity.AccountingObjectUnionImageSyncEntity
import com.itrocket.union.image.domain.ImageDomain
import java.io.File

fun AccountingObjectUnionImageSyncEntity.toDomain(imageFile: File) = ImageDomain.create(
    imageId = unionImageId,
    isMainImage = isMainImage,
    imageFile = imageFile
)

fun ImageDomain.toSyncEntity(accountingObjectId: String, userInserted: String?) =
    AccountingObjectUnionImageSyncEntity(
        isMainImage = isMainImage,
        accountingObjectId = accountingObjectId,
        unionImageId = imageId,
        userInserted = userInserted
    )
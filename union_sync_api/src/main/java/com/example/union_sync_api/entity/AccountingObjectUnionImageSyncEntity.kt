package com.example.union_sync_api.entity

data class AccountingObjectUnionImageSyncEntity(
    val unionImageId: String,
    val isMainImage: Boolean,
    val accountingObjectId: String,
    val userInserted: String?
)
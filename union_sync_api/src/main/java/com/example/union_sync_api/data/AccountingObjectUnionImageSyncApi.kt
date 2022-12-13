package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectUnionImageSyncEntity

interface AccountingObjectUnionImageSyncApi {

    suspend fun getAccountingObjectImagesById(accountingObjectId: String): List<AccountingObjectUnionImageSyncEntity>

    suspend fun saveAccountingObjectImage(syncEntity: AccountingObjectUnionImageSyncEntity)
}
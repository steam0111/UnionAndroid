package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectUnionImageSyncEntity
import kotlinx.coroutines.flow.Flow

interface AccountingObjectUnionImageSyncApi {

    suspend fun getAccountingObjectImagesByIdFlow(accountingObjectId: String): Flow<List<AccountingObjectUnionImageSyncEntity>>

    suspend fun saveAccountingObjectImage(syncEntity: AccountingObjectUnionImageSyncEntity)
}
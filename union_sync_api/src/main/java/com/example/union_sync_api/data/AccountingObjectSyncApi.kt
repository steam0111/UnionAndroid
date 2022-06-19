package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import kotlinx.coroutines.flow.Flow

interface AccountingObjectSyncApi {
    suspend fun getAccountingObjects(): Flow<List<AccountingObjectSyncEntity>>
}
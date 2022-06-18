package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectSyncEntity

interface AccountingObjectSyncApi {
    suspend fun getAccountingObjects(): List<AccountingObjectSyncEntity>
}
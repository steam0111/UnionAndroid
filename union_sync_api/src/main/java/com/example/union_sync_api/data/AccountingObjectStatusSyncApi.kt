package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectStatusSyncEntity

interface AccountingObjectStatusSyncApi {
    suspend fun getStatuses(): List<AccountingObjectStatusSyncEntity>
}
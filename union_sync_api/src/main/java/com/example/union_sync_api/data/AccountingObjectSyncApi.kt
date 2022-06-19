package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import kotlinx.coroutines.flow.Flow

interface AccountingObjectSyncApi {
    suspend fun getAccountingObjects(
        organizationId: String? = null,
        molId: String? = null,
        exploitingId: String? = null
    ): Flow<List<AccountingObjectSyncEntity>>
}
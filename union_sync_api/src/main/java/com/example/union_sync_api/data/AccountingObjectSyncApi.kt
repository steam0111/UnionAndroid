package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import kotlinx.coroutines.flow.Flow

interface AccountingObjectSyncApi {
    suspend fun getAccountingObjects(
        organizationId: String? = null,
        exploitingId: String? = null,
        textQuery: String? = null
    ): Flow<List<AccountingObjectSyncEntity>>

    suspend fun getAccountingObjectsByRfids(accountingObjectRfids: List<String>): List<AccountingObjectSyncEntity>

    suspend fun getAccountingObjectsByBarcode(accountingObjectBarcode: String): AccountingObjectSyncEntity?

    suspend fun getAccountingObjectDetailById(id: String): AccountingObjectDetailSyncEntity
}
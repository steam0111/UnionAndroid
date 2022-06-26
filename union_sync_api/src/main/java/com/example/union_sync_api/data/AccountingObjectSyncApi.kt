package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectSyncEntity

interface AccountingObjectSyncApi {
    suspend fun getAccountingObjects(
        organizationId: String? = null,
        exploitingId: String? = null,
        molId: String? = null,
        departmentId: String? = null,
        producerId: String? = null,
        equipmentTypeId: String? = null,
        providerId: String? = null,
        rfids: List<String>? = null,
        barcode: String? = null,
        textQuery: String? = null,
        accountingObjectsIds: List<String>? = null
    ): List<AccountingObjectSyncEntity>

    suspend fun getAccountingObjectDetailById(id: String): AccountingObjectDetailSyncEntity
}
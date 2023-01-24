package com.itrocket.union.accountingObjects.domain.dependencies

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.AccountingObjectUpdateSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain

interface AccountingObjectRepository {

    suspend fun getAccountingObjects(
        textQuery: String? = null,
        params: List<ParamDomain>,
        selectedLocationIds: List<String?>?,
        structuralIds: List<String?>?,
        offset: Long?,
        limit: Long?,
        showUtilized: Boolean
    ): List<AccountingObjectDomain>

    suspend fun getAccountingObjectsCount(
        textQuery: String? = null,
        params: List<ParamDomain>,
        selectedLocationIds: List<String?>?,
        structuralIds: List<String?>?,
        showUtilized: Boolean
    ): Long

    suspend fun getAccountingObjectsByIds(ids: List<String>): List<AccountingObjectSyncEntity>

    suspend fun getAccountingObjectsByRfids(rfids: List<String>): List<AccountingObjectDomain>

    suspend fun getAccountingObjectsByBarcode(
        barcode: String?,
        serialNumber: String?
    ): AccountingObjectDomain?

    suspend fun updateAccountingObjects(accountingObjects: List<AccountingObjectUpdateSyncEntity>)

    suspend fun getAvailableStatus(): ParamDomain

    suspend fun writeOffAccountingObjects(accountingObjects: List<AccountingObjectDomain>)

    suspend fun getPropertyCount(): Long
}
package com.example.union_sync_api.data

import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectScanningData
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.AccountingObjectUpdateSyncEntity
import com.example.union_sync_api.entity.AccountingObjectWriteOff
import com.example.union_sync_api.entity.LabelType
import kotlinx.coroutines.flow.Flow

interface AccountingObjectSyncApi {
    suspend fun getAccountingObjects(
        exploitingId: String? = null,
        molId: String? = null,
        producerId: String? = null,
        equipmentTypeId: String? = null,
        providerId: String? = null,
        rfids: List<String>? = null,
        barcode: String? = null,
        statusId: String? = null,
        textQuery: String? = null,
        accountingObjectsIds: List<String>? = null,
        locationIds: List<String?>? = null,
        structuralId: List<String?>? = null,
        offset: Long? = null,
        limit: Long? = null,
        serialNumber: String? = null,
        isShowUtilised: Boolean = true
    ): List<AccountingObjectSyncEntity>

    suspend fun getAccountingObjectsCount(
        exploitingId: String? = null,
        molId: String? = null,
        producerId: String? = null,
        equipmentTypeId: String? = null,
        providerId: String? = null,
        rfids: List<String>? = null,
        barcode: String? = null,
        statusId: String? = null,
        textQuery: String? = null,
        accountingObjectsIds: List<String>? = null,
        locationIds: List<String?>? = null,
        structuralIds: List<String?>?,
        showUtilized: Boolean = true
    ): Long

    suspend fun getAccountingObjectDetailById(id: String): AccountingObjectDetailSyncEntity

    suspend fun getAccountingObjectDetailByParams(
        rfid: String? = null,
        barcode: String? = null,
        factoryNumber: String? = null
    ): AccountingObjectDetailSyncEntity

    suspend fun getAccountingObjectDetailByIdFlow(id: String): Flow<AccountingObjectDetailSyncEntity>

    suspend fun updateAccountingObjects(accountingObjects: List<AccountingObjectUpdateSyncEntity>)

    suspend fun writeOffAccountingObject(accountingObjectWriteOff: AccountingObjectWriteOff)

    suspend fun writeOffAccountingObjects(accountingObjectsWriteOff: List<AccountingObjectWriteOff>)

    suspend fun updateAccountingObjectScanningData(accountingObject: AccountingObjectScanningData)

    suspend fun updateAccountingObjectLabelType(accountingObject: LabelType)

    suspend fun updateAccountingObjectMarked(accountingObjectId: String, rfid: String)

    suspend fun getPropertyCount(): Long
}
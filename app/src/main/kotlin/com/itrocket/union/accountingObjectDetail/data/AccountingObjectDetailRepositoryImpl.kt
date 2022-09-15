package com.itrocket.union.accountingObjectDetail.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.itrocket.union.accountingObjectDetail.data.mapper.toAccountingObjectDetailDomain
import com.itrocket.union.accountingObjectDetail.data.mapper.toAccountingObjectScanningData
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountingObjectDetailRepositoryImpl(
    private val syncApi: AccountingObjectSyncApi
) : AccountingObjectDetailRepository {

    override suspend fun getAccountingObject(id: String): AccountingObjectDomain {
        return syncApi.getAccountingObjectDetailById(id).toAccountingObjectDetailDomain()
    }

    override suspend fun getAccountingObjectByParams(
        rfid: String?,
        barcode: String?,
        factoryNumber: String?
    ): AccountingObjectDomain {
        return syncApi.getAccountingObjectDetailByParams(
            rfid = rfid,
            barcode = barcode,
            factoryNumber = factoryNumber
        ).toAccountingObjectDetailDomain()
    }

    override suspend fun getAccountingObjectFlow(id: String): Flow<AccountingObjectDomain> {
        return syncApi.getAccountingObjectDetailByIdFlow(id)
            .map { it.toAccountingObjectDetailDomain() }
    }

    override suspend fun updateScanningData(accountingObject: AccountingObjectDomain) {
        return syncApi.updateAccountingObjectScanningData(accountingObject.toAccountingObjectScanningData())
    }
}
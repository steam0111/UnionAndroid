package com.itrocket.union.accountingObjectDetail.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.coroutines.flow.Flow

interface AccountingObjectDetailRepository {
    suspend fun getAccountingObject(id: String): AccountingObjectDomain

    suspend fun getAccountingObjectByParams(
        rfid: String? = null,
        barcode: String? = null,
        factoryNumber: String? = null
    ): AccountingObjectDomain

    suspend fun getAccountingObjectFlow(id: String): Flow<AccountingObjectDomain>

    suspend fun updateScanningData(accountingObject: AccountingObjectDomain)
}
package com.itrocket.union.accountingObjects.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.flow.Flow

interface AccountingObjectRepository {

    suspend fun getAccountingObjects(params: List<ParamDomain>): Flow<List<AccountingObjectDomain>>

    suspend fun getAccountingObjectsByRfids(rfids: List<String>): List<AccountingObjectDomain>

    suspend fun getAccountingObjectsByBarcode(barcode: String): AccountingObjectDomain?
}
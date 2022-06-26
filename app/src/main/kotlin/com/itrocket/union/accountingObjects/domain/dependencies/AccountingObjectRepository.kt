package com.itrocket.union.accountingObjects.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain

interface AccountingObjectRepository {

    suspend fun getAccountingObjects(textQuery: String? = null, params: List<ParamDomain>): List<AccountingObjectDomain>

    suspend fun getAccountingObjectsByRfids(rfids: List<String>): List<AccountingObjectDomain>

    suspend fun getAccountingObjectsByBarcode(barcode: String): AccountingObjectDomain?
}
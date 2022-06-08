package com.itrocket.union.accountingObjects.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain

interface AccountingObjectRepository {

    suspend fun getAccountingObjects(): List<AccountingObjectDomain>

    suspend fun filterAccountingObjectsByParams(params: List<ParamDomain>): List<AccountingObjectDomain>
}
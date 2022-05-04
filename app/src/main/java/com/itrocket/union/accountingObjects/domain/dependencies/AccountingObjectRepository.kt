package com.itrocket.union.accountingObjects.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

interface AccountingObjectRepository {

    suspend fun getAccountingObjects(): List<AccountingObjectDomain>
}
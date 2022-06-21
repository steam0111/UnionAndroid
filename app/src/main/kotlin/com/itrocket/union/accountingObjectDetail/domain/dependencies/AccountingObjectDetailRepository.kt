package com.itrocket.union.accountingObjectDetail.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

interface AccountingObjectDetailRepository {
    suspend fun getAccountingObject(id: String): AccountingObjectDomain
}
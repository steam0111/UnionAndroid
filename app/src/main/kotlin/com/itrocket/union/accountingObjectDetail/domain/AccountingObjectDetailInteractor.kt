package com.itrocket.union.accountingObjectDetail.domain

import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.coroutines.withContext

class AccountingObjectDetailInteractor(
    private val repository: AccountingObjectDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getAccountingObject(id: String): AccountingObjectDomain =
        withContext(coreDispatchers.io) {
            repository.getAccountingObject(id)
        }
}
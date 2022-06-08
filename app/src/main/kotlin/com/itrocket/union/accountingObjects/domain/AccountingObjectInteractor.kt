package com.itrocket.union.accountingObjects.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain

class AccountingObjectInteractor(
    private val repository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getAccountingObjects() = withContext(coreDispatchers.io) {
        repository.getAccountingObjects()
    }

    suspend fun filterAccountingObjectsByParams(params: List<ParamDomain>): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            repository.filterAccountingObjectsByParams(params)
        }
    }
}
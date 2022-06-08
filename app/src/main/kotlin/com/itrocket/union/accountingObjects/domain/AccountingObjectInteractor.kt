package com.itrocket.union.accountingObjects.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.core.base.CoreDispatchers

class AccountingObjectInteractor(
    private val repository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getAccountingObjects() = withContext(coreDispatchers.io) {
        repository.getAccountingObjects()
    }
}
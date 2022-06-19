package com.itrocket.union.accountingObjects.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AccountingObjectInteractor(
    private val repository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getAccountingObjects(params: List<ParamDomain>): Flow<List<AccountingObjectDomain>> =
        withContext(coreDispatchers.io) {
            //filter params
            repository.getAccountingObjects()
        }

    suspend fun getAccountingObjectsByParams(params: List<ParamDomain>): Flow<List<AccountingObjectDomain>> {
        return withContext(coreDispatchers.io) {
            repository.getAccountingObjectsByParams(params)
        }
    }
}
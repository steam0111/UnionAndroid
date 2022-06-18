package com.itrocket.union.accountingObjects.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.ParamValueDomain

class AccountingObjectInteractor(
    private val repository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getAccountingObjects(params: List<ParamDomain>) =
        withContext(coreDispatchers.io) {
            //filter params
            repository.getAccountingObjects()
        }

    fun getParamValues(params: List<ParamDomain>?): List<ParamValueDomain> {
        return params?.map { it.paramValue }?.filterNotNull() ?: listOf()
    }

    suspend fun getAccountingObjectsByParams(params: List<ParamDomain>): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            repository.getAccountingObjectsByParams(params)
        }
    }
}
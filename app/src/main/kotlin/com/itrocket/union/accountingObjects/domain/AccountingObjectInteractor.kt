package com.itrocket.union.accountingObjects.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AccountingObjectInteractor(
    private val repository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getAccountingObjects(
        params: List<ParamDomain>,
        selectedAccountingObjectIds: List<String> = listOf()
    ): Flow<List<AccountingObjectDomain>> =
        withContext(coreDispatchers.io) {
            //filter params
            repository.getAccountingObjects(params).map {
                it.filter {
                    !selectedAccountingObjectIds.contains(it.id)
                }
            }
        }
}
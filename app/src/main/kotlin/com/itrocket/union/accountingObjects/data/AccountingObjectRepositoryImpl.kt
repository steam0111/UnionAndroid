package com.itrocket.union.accountingObjects.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getOrganizationId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AccountingObjectRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AccountingObjectSyncApi,
) : AccountingObjectRepository {

    override suspend fun getAccountingObjects(params: List<ParamDomain>): Flow<List<AccountingObjectDomain>> =
        withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects(
                organizationId = params.getOrganizationId(),
                exploitingId = params.getExploitingId()
            ).map { it.map() }
        }
}
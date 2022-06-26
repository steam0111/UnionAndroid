package com.itrocket.union.accountingObjects.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getOrganizationId
import kotlinx.coroutines.withContext

class AccountingObjectRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AccountingObjectSyncApi,
) : AccountingObjectRepository {

    override suspend fun getAccountingObjects(params: List<ParamDomain>): List<AccountingObjectDomain> =
        withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects(
                organizationId = params.getOrganizationId(),
                exploitingId = params.getExploitingId()
            ).map { it.map() }
        }

    override suspend fun getAccountingObjectsByRfids(rfids: List<String>): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects(rfids = rfids).map()
        }
    }

    override suspend fun getAccountingObjectsByBarcode(barcode: String): AccountingObjectDomain? {
        return withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects(barcode = barcode).firstOrNull()?.map()
        }
    }

}
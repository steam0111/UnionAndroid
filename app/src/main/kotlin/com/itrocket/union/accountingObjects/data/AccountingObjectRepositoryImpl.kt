package com.itrocket.union.accountingObjects.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getDepartmentId
import com.itrocket.union.manual.getEquipmentTypeId
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getOrganizationId
import com.itrocket.union.manual.getProducerId
import com.itrocket.union.manual.getProviderId
import com.itrocket.union.manual.getStatusId
import kotlinx.coroutines.withContext

class AccountingObjectRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AccountingObjectSyncApi,
) : AccountingObjectRepository {

    override suspend fun getAccountingObjects(
        textQuery: String?,
        params: List<ParamDomain>
    ): List<AccountingObjectDomain> =
        withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects(
                textQuery = textQuery,
                organizationId = params.getOrganizationId(),
                exploitingId = params.getExploitingId(),
                molId = params.getMolId(),
                departmentId = params.getDepartmentId(),
                producerId = params.getProducerId(),
                equipmentTypeId = params.getEquipmentTypeId(),
                providerId = params.getProviderId(),
                statusId = params.getStatusId()
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
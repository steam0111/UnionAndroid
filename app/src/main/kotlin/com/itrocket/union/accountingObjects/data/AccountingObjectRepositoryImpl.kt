package com.itrocket.union.accountingObjects.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.AccountingObjectUpdateSyncEntity
import com.example.union_sync_api.entity.EnumType
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectStatus
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getEquipmentTypeId
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getMolInDepartmentId
import com.itrocket.union.manual.getProducerId
import com.itrocket.union.manual.getProviderId
import com.itrocket.union.manual.getStatusId
import com.itrocket.union.manual.toParam
import kotlinx.coroutines.withContext

class AccountingObjectRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AccountingObjectSyncApi,
    private val enumsSyncApi: EnumsSyncApi
) : AccountingObjectRepository {

    override suspend fun getAccountingObjects(
        textQuery: String?,
        params: List<ParamDomain>,
        selectedLocationIds: List<String?>?,
        structuralIds: List<String?>?,
        offset: Long?,
        limit: Long?,
        showUtilized: Boolean
    ): List<AccountingObjectDomain> =
        withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects(
                textQuery = textQuery,
                exploitingId = params.getExploitingId(),
                molId = params.getMolInDepartmentId() ?: params.getMolId(),
                producerId = params.getProducerId(),
                equipmentTypeId = params.getEquipmentTypeId(),
                providerId = params.getProviderId(),
                statusId = params.getStatusId(),
                locationIds = selectedLocationIds,
                structuralId = structuralIds,
                offset = offset,
                limit = limit,
                isShowUtilised = showUtilized
            ).map { it.map() }
        }

    override suspend fun getAccountingObjectsCount(
        textQuery: String?,
        params: List<ParamDomain>,
        selectedLocationIds: List<String?>?,
        structuralIds: List<String?>?,
        showUtilized: Boolean
    ): Long =
        withContext(coreDispatchers.io) {
            syncApi.getAccountingObjectsCount(
                textQuery = textQuery,
                exploitingId = params.getExploitingId(),
                molId = params.getMolId(),
                producerId = params.getProducerId(),
                equipmentTypeId = params.getEquipmentTypeId(),
                providerId = params.getProviderId(),
                statusId = params.getStatusId(),
                locationIds = selectedLocationIds,
                structuralIds = structuralIds,
                showUtilized = showUtilized
            )
        }

    override suspend fun getAccountingObjectsByIds(ids: List<String>): List<AccountingObjectSyncEntity> {
        return withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects(accountingObjectsIds = ids)
        }
    }

    override suspend fun getAccountingObjectsByRfids(rfids: List<String>): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects(rfids = rfids).map()
        }
    }

    override suspend fun getAccountingObjectsByBarcode(
        barcode: String?,
        serialNumber: String?
    ): AccountingObjectDomain? {
        return withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects(barcode = barcode, serialNumber = serialNumber)
                .firstOrNull()?.map()
        }
    }

    override suspend fun updateAccountingObjects(accountingObjects: List<AccountingObjectUpdateSyncEntity>) {
        withContext(coreDispatchers.io) {
            syncApi.updateAccountingObjects(accountingObjects = accountingObjects)
        }
    }

    override suspend fun getAvailableStatus(): ParamDomain {
        return withContext(coreDispatchers.io) {
            enumsSyncApi.getByCompoundId(
                id = AccountingObjectStatus.AVAILABLE.name,
                enumType = EnumType.ACCOUNTING_OBJECT_STATUS
            )?.toParam(ManualType.STATUS)
        } ?: ParamDomain(type = ManualType.STATUS, value = "")
    }
}
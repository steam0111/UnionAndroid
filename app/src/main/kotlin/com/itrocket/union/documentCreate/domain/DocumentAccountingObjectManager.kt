package com.itrocket.union.documentCreate.domain

import com.example.union_sync_api.data.AccountingObjectStatusSyncApi
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.toAccountingObjectUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getLocationIds
import kotlinx.coroutines.withContext

class DocumentAccountingObjectManager(
    private val coreDispatchers: CoreDispatchers,
    private val accountingObjectStatusSyncApi: AccountingObjectStatusSyncApi,
    private val repository: AccountingObjectRepository
) {

    suspend fun changeAccountingObjectsAfterConduct(
        accountingObjects: List<AccountingObjectDomain>,
        documentTypeDomain: DocumentTypeDomain,
        params: List<ParamDomain>
    ) {
        withContext(coreDispatchers.io) {
            val accountingObjectIds = accountingObjects.map { it.id }
            val locationIds = params.getLocationIds()
            val exploitingId = params.getExploitingId()
            val newAccountingObjects = when (documentTypeDomain) {
                DocumentTypeDomain.EXTRADITION -> changeExtradition(
                    accountingObjectIds = accountingObjectIds,
                    exploitingId = requireNotNull(exploitingId),
                    locationIds = locationIds
                )
                DocumentTypeDomain.RETURN -> changeReturn(
                    accountingObjectIds = accountingObjectIds,
                    locationIds = locationIds
                )
                DocumentTypeDomain.MOVING -> changeMove(
                    accountingObjectIds = accountingObjectIds,
                    locationIds = locationIds
                )
                else -> {
                    listOf()
                }
            }
            repository.updateAccountingObjects(newAccountingObjects.map {
                it.toAccountingObjectUpdateSyncEntity()
            })
        }
    }

    private suspend fun changeExtradition(
        accountingObjectIds: List<String>,
        exploitingId: String,
        locationIds: List<String>?
    ): List<AccountingObjectSyncEntity> {
        return withContext(coreDispatchers.io) {
            val accountingObjects = repository.getAccountingObjectsByIds(accountingObjectIds)
            val newStatus =
                accountingObjectStatusSyncApi.getStatuses(id = ObjectStatusType.GIVEN.name).first()
            accountingObjects.map {
                it.copy(
                    exploitingEmployeeId = exploitingId,
                    status = newStatus,
                    statusId = newStatus.id,
                    locationId = locationIds?.lastOrNull() ?: it.locationId
                )
            }
        }
    }

    private suspend fun changeReturn(
        accountingObjectIds: List<String>,
        locationIds: List<String>?
    ): List<AccountingObjectSyncEntity> {
        return withContext(coreDispatchers.io) {
            val accountingObjects = repository.getAccountingObjectsByIds(accountingObjectIds)
            val newStatus =
                accountingObjectStatusSyncApi.getStatuses(id = ObjectStatusType.AVAILABLE.name)
                    .first()
            accountingObjects.map {
                it.copy(
                    exploitingEmployeeId = null,
                    status = newStatus,
                    statusId = newStatus.id,
                    locationId = locationIds?.lastOrNull() ?: it.locationId
                )
            }
        }
    }

    private suspend fun changeMove(
        accountingObjectIds: List<String>,
        locationIds: List<String>?
    ): List<AccountingObjectSyncEntity> {
        return withContext(coreDispatchers.io) {
            val accountingObjects = repository.getAccountingObjectsByIds(accountingObjectIds)
            val newStatus =
                accountingObjectStatusSyncApi.getStatuses(id = ObjectStatusType.TRANSIT.name)
                    .first()
            accountingObjects.map {
                it.copy(
                    status = newStatus,
                    statusId = newStatus.id,
                    locationId = locationIds?.lastOrNull() ?: it.locationId
                )
            }
        }
    }
}
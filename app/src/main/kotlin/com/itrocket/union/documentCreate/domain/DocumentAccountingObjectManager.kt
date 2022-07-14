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
import com.itrocket.union.manual.getFilterLocationLastId
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
            val exploitingId = params.getExploitingId()
            val newAccountingObjects = when (documentTypeDomain) {
                DocumentTypeDomain.GIVE -> changeExtradition(
                    accountingObjectIds = accountingObjectIds,
                    exploitingId = requireNotNull(exploitingId),
                    locationId = params.getFilterLocationLastId()
                )
                DocumentTypeDomain.RETURN -> changeReturn(
                    accountingObjectIds = accountingObjectIds,
                    locationId = params.getFilterLocationLastId()
                )
                DocumentTypeDomain.RELOCATION -> changeMove(
                    accountingObjectIds = accountingObjectIds,
                    locationId = params.getFilterLocationLastId()
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
        locationId: String?
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
                    locationId = locationId
                )
            }
        }
    }

    private suspend fun changeReturn(
        accountingObjectIds: List<String>,
        locationId: String?
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
                    locationId = locationId
                )
            }
        }
    }

    private suspend fun changeMove(
        accountingObjectIds: List<String>,
        locationId: String?
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
                    locationId = locationId
                )
            }
        }
    }
}
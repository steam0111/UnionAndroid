package com.itrocket.union.documentCreate.domain

import com.example.union_sync_api.data.AccountingObjectStatusSyncApi
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.toAccountingObjectUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getExploitingId
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.manual.getFilterStructuralLastId
import com.itrocket.union.manual.getMolId
import kotlinx.coroutines.withContext

class DocumentAccountingObjectManager(
    private val coreDispatchers: CoreDispatchers,
    private val accountingObjectStatusSyncApi: AccountingObjectStatusSyncApi,
    private val repository: AccountingObjectRepository,
    private val authMainInteractor: AuthMainInteractor
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
                    exploitingId = exploitingId,
                    locationToId = params.getFilterLocationLastId(ManualType.LOCATION_TO),
                    structuralId = params.getFilterStructuralLastId()
                )
                DocumentTypeDomain.RETURN -> changeReturn(
                    accountingObjectIds = accountingObjectIds,
                    locationToId = params.getFilterLocationLastId(ManualType.LOCATION_TO),
                    structuralId = params.getFilterStructuralLastId()
                )
                DocumentTypeDomain.RELOCATION -> changeMove(
                    accountingObjectIds = accountingObjectIds,
                    locationToId = params.getFilterLocationLastId(ManualType.RELOCATION_LOCATION_TO),
                    structuralId = params.getFilterStructuralLastId(),
                    molId = params.getMolId()
                )
                else -> {
                    listOf()
                }
            }
            repository.updateAccountingObjects(newAccountingObjects.map {
                it.toAccountingObjectUpdateSyncEntity(authMainInteractor.getLogin())
            })
        }
    }

    private suspend fun changeExtradition(
        accountingObjectIds: List<String>,
        exploitingId: String?,
        locationToId: String?,
        structuralId: String?
    ): List<AccountingObjectSyncEntity> {
        return withContext(coreDispatchers.io) {
            val accountingObjects = repository.getAccountingObjectsByIds(accountingObjectIds)
            val newStatus =
                accountingObjectStatusSyncApi.getStatuses(id = ObjectStatusType.GIVEN.name)
                    .firstOrNull()
            accountingObjects.map {
                it.copy(
                    exploitingEmployeeId = exploitingId,
                    status = newStatus,
                    statusId = newStatus?.id,
                    locationId = locationToId ?: it.locationId,
                    structuralId = structuralId ?: it.structuralId
                )
            }
        }
    }

    private suspend fun changeReturn(
        accountingObjectIds: List<String>,
        locationToId: String?,
        structuralId: String?
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
                    locationId = locationToId ?: it.locationId,
                    structuralId = structuralId ?: it.structuralId
                )
            }
        }
    }

    private suspend fun changeMove(
        accountingObjectIds: List<String>,
        locationToId: String?,
        structuralId: String?,
        molId: String?
    ): List<AccountingObjectSyncEntity> {
        return withContext(coreDispatchers.io) {
            val accountingObjects = repository.getAccountingObjectsByIds(accountingObjectIds)
            accountingObjects.map {
                it.copy(
                    locationId = locationToId ?: it.locationId,
                    molId = molId ?: it.molId,
                    structuralId = structuralId ?: it.structuralId
                )
            }
        }
    }
}
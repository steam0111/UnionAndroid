package com.itrocket.union.transit.domain

import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.EnumType
import com.example.union_sync_api.entity.toAccountingObjectUpdateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.manual.getMolId
import kotlinx.coroutines.withContext

class TransitAccountingObjectManager(
    private val coreDispatchers: CoreDispatchers,
    private val enumsSyncApi: EnumsSyncApi,
    private val repository: AccountingObjectRepository,
    private val authMainInteractor: AuthMainInteractor
) {

    suspend fun changeAccountingObjectsAfterConduct(
        accountingObjects: List<AccountingObjectDomain>,
        transitTypeDomain: TransitTypeDomain,
        params: List<ParamDomain>
    ) {
        withContext(coreDispatchers.io) {
            val accountingObjectIds = accountingObjects.map { it.id }
            val locationType = if (transitTypeDomain == TransitTypeDomain.TRANSIT_SENDING) {
                ManualType.TRANSIT
            } else {
                ManualType.LOCATION_TO
            }
            val locationId = params.getFilterLocationLastId(locationType)
            val newAccountingObjects = when (transitTypeDomain) {
                TransitTypeDomain.TRANSIT_SENDING -> changeTransitSending(
                    accountingObjectIds = accountingObjectIds,
                    locationToId = locationId,
                )
                TransitTypeDomain.TRANSIT_RECEPTION -> changeTransitReception(
                    accountingObjectIds = accountingObjectIds,
                    molId = params.getMolId(),
                    locationToId = locationId,
                )
            }
            repository.updateAccountingObjects(newAccountingObjects.map {
                it.toAccountingObjectUpdateSyncEntity(authMainInteractor.getLogin())
            })
        }
    }

    private suspend fun changeTransitSending(
        accountingObjectIds: List<String>,
        locationToId: String?,
    ): List<AccountingObjectSyncEntity> {
        return withContext(coreDispatchers.io) {
            val status =
                enumsSyncApi.getAllByType(
                    id = AccountingObjectStatus.TRANSIT.name,
                    enumType = EnumType.ACCOUNTING_OBJECT_STATUS
                )
                    .firstOrNull()

            val accountingObjects = repository.getAccountingObjectsByIds(accountingObjectIds)
            accountingObjects.map {
                it.copy(
                    locationId = locationToId ?: it.locationId,
                    status = status
                )
            }
        }
    }

    private suspend fun changeTransitReception(
        accountingObjectIds: List<String>,
        locationToId: String?,
        molId: String?
    ): List<AccountingObjectSyncEntity> {
        return withContext(coreDispatchers.io) {
            val status =
                enumsSyncApi.getAllByType(
                    id = AccountingObjectStatus.AVAILABLE.name,
                    enumType = EnumType.ACCOUNTING_OBJECT_STATUS
                )
                    .firstOrNull()

            val accountingObjects = repository.getAccountingObjectsByIds(accountingObjectIds)
            accountingObjects.map {
                it.copy(
                    locationId = locationToId ?: it.locationId,
                    molId = molId ?: it.molId,
                    status = status
                )
            }
        }
    }
}
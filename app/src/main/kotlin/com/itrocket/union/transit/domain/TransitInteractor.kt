package com.itrocket.union.transit.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.transit.domain.dependencies.TransitRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.toCreateSyncEntity
import com.itrocket.union.documents.domain.entity.toTransitCreateSyncEntity
import com.itrocket.union.documents.domain.entity.toTransitUpdateSyncEntity
import com.itrocket.union.documents.domain.entity.toUpdateSyncEntity
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain

class TransitInteractor(
    private val repository: TransitRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getTransitById(id: String): DocumentDomain{
        return withContext(coreDispatchers.io) {
            repository.getTransitById(id)
        }
    }

    suspend fun createOrUpdateDocument(
        transit: DocumentDomain,
        accountingObjects: List<AccountingObjectDomain>,
        reserves: List<ReservesDomain>,
        params: List<ParamDomain>,
        status: DocumentStatus,
        transitTypeDomain: TransitTypeDomain
    ): String {
        return withContext(coreDispatchers.io) {
            if (!transit.isDocumentExists) {
                repository.createTransit(
                    transit.copy(
                        accountingObjects = accountingObjects,
                        params = params,
                        documentStatus = status,
                        documentStatusId = status.name,
                        reserves = reserves,
                        transitType = transitTypeDomain
                    ).toTransitCreateSyncEntity()
                )
            } else {
                repository.updateTransit(
                    transit.copy(
                        accountingObjects = accountingObjects,
                        params = params,
                        documentStatus = status,
                        documentStatusId = status.name,
                        reserves = reserves,
                        transitType = transitTypeDomain
                    ).toTransitUpdateSyncEntity()
                )
                transit.id.orEmpty()
            }
        }
    }
}
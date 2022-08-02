package com.itrocket.union.accountingObjects.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getFilterLocationLastId
import kotlinx.coroutines.withContext

class AccountingObjectInteractor(
    private val repository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers,
    private val locationRepository: LocationRepository
) {

    suspend fun getAccountingObjects(
        searchQuery: String = "",
        params: List<ParamDomain>,
        selectedAccountingObjectIds: List<String> = listOf()
    ): List<AccountingObjectDomain> =
        withContext(coreDispatchers.io) {
            val lastLocationId = params.getFilterLocationLastId()
            val filterLocationIds = if (lastLocationId == null) {
                null
            } else {
                locationRepository.getAllLocationsIdsByParent(lastLocationId)
            }

            repository.getAccountingObjects(
                searchQuery,
                params,
                filterLocationIds
            ).filter {
                !selectedAccountingObjectIds.contains(it.id)
            }
        }

    suspend fun getFilters(isFromDocument: Boolean): List<ParamDomain> {
        return listOf(
            getStatusFilter(isFromDocument),
            ParamDomain(
                type = ManualType.ORGANIZATION,
                value = ""
            ),
            ParamDomain(
                type = ManualType.MOL,
                value = ""
            ),
            ParamDomain(
                type = ManualType.EXPLOITING,
                value = ""
            ),
            LocationParamDomain(
                locations = listOf()
            ),
            ParamDomain(
                type = ManualType.DEPARTMENT,
                value = ""
            ),
            ParamDomain(
                type = ManualType.PROVIDER,
                value = ""
            ),
            ParamDomain(
                type = ManualType.PRODUCER,
                value = ""
            ),
            ParamDomain(
                type = ManualType.EQUIPMENT_TYPE,
                value = ""
            )
        )
    }

    private suspend fun getStatusFilter(isFromDocument: Boolean): ParamDomain {
        return if (isFromDocument) {
            repository.getAvailableStatus()
        } else {
            ParamDomain(
                type = ManualType.STATUS,
                value = ""
            )
        }
    }
}
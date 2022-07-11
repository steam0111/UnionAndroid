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
            //filter params
            repository.getAccountingObjects(
                searchQuery,
                params,
                locationRepository.getAllLocationsIdsByParent(params.getFilterLocationLastId())
            ).filter {
                !selectedAccountingObjectIds.contains(it.id)
            }
        }

    fun getFilters(): List<ParamDomain> {
        return listOf(
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
                type = ManualType.STATUS,
                value = ""
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
}
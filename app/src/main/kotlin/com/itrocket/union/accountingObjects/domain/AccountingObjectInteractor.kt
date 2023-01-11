package com.itrocket.union.accountingObjects.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.PropertyCount
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.manual.CheckBoxParamDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.manual.getFilterShowUtilizedAccountingObjects
import com.itrocket.union.manual.getFilterStructuralLastId
import com.itrocket.union.structural.domain.dependencies.StructuralRepository
import kotlinx.coroutines.withContext

class AccountingObjectInteractor(
    private val repository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers,
    private val locationRepository: LocationRepository,
    private val structuralRepository: StructuralRepository
) {

    suspend fun getAccountingObjects(
        locationManualType: ManualType,
        searchQuery: String = "",
        params: List<ParamDomain>,
        selectedAccountingObjectIds: List<String> = listOf(),
        offset: Long? = null,
        limit: Long? = null,
        showUtilized: Boolean = true
    ): List<AccountingObjectDomain> =
        withContext(coreDispatchers.io) {
            val lastLocationId = params.getFilterLocationLastId(locationManualType)
            val filterLocationIds = if (lastLocationId == null) {
                null
            } else {
                locationRepository.getAllLocationsIdsByParent(lastLocationId)
            }

            val lastStructuralId = params.getFilterStructuralLastId(ManualType.STRUCTURAL)
            val filterStructuralIds = if (lastStructuralId == null) {
                null
            } else {
                structuralRepository.getAllStructuralsIdsByParent(lastStructuralId)
            }

            repository.getAccountingObjects(
                textQuery = searchQuery,
                params = params,
                selectedLocationIds = filterLocationIds,
                structuralIds = filterStructuralIds,
                offset = offset,
                limit = limit,
                showUtilized = params.getFilterShowUtilizedAccountingObjects() && showUtilized
            ).filter {
                !selectedAccountingObjectIds.contains(it.id)
            }
        }

    suspend fun getFilters(isFromDocument: Boolean): List<ParamDomain> {
        return buildList {
            add(getStatusFilter(isFromDocument))
            add(
                StructuralParamDomain(
                    structurals = listOf(),
                    manualType = ManualType.STRUCTURAL
                )
            )
            add(
                ParamDomain(
                    type = ManualType.MOL,
                    value = ""
                )
            )
            add(
                ParamDomain(
                    type = ManualType.EXPLOITING,
                    value = ""
                )
            )
            add(
                LocationParamDomain(
                    locations = listOf()
                )
            )
            add(
                ParamDomain(
                    type = ManualType.PROVIDER,
                    value = ""
                )
            )
            add(
                ParamDomain(
                    type = ManualType.PRODUCER,
                    value = ""
                )
            )
            add(
                ParamDomain(
                    type = ManualType.EQUIPMENT_TYPE,
                    value = ""
                )
            )
            if (isFromDocument) {
                add(
                    CheckBoxParamDomain(
                        isChecked = false,
                        manualType = ManualType.CHECKBOX_SHOW_UTILIZED
                    )
                )
            }
        }
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

    suspend fun getPropertyCount(): PropertyCount = withContext(coreDispatchers.io) {
        repository.getPropertyCount()
    }
}
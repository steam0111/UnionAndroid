package com.itrocket.union.filter.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.employees.domain.dependencies.EmployeeRepository
import com.itrocket.union.filter.domain.dependencies.FilterRepository
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.manual.CheckBoxParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getFilterHideZeroReserves
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.manual.getFilterShowUtilizedAccountingObjects
import com.itrocket.union.manual.getFilterStructuralLastId
import com.itrocket.union.nomenclature.domain.dependencies.NomenclatureRepository
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.structural.domain.dependencies.StructuralRepository

class FilterInteractor(
    private val repository: FilterRepository,
    private val accountingObjectRepository: AccountingObjectRepository,
    private val employeeRepository: EmployeeRepository,
    private val structuralRepository: StructuralRepository,
    private val nomenclatureRepository: NomenclatureRepository,
    private val coreDispatchers: CoreDispatchers,
    private val documentRepository: DocumentRepository,
    private val inventoriesRepository: InventoryRepository,
    private val reservesRepository: ReservesRepository,
    private val locationRepository: LocationRepository
) {
    fun getFilters() = repository.getFilters()

    fun dropFilterFields(filters: List<ParamDomain>): List<ParamDomain> {
        return filters.toMutableList().map {
            it.toInitialState()
        }
    }

    fun changeFilters(
        filters: List<ParamDomain>,
        newFilters: List<ParamDomain>
    ): List<ParamDomain> {
        val mutableFilters = filters.toMutableList()
        mutableFilters.forEachIndexed { index, paramDomain ->
            val newParam = newFilters.find { it.type == paramDomain.type }
            if (newParam != null) {
                mutableFilters[index] = newParam
            }
        }
        return mutableFilters
    }

    fun changeCheckboxFilter(
        filters: List<ParamDomain>,
        isChecked: Boolean,
        manualType: ManualType
    ): List<ParamDomain> {
        val checkBoxIndex = filters.indexOfFirst { it.type == manualType }
        if (checkBoxIndex < 0) return filters
        val newFilters = filters.toMutableList()
        newFilters[checkBoxIndex] =
            CheckBoxParamDomain(isChecked = isChecked, manualType = manualType)
        return newFilters
    }

    suspend fun getResultCount(from: CatalogType?, params: List<ParamDomain>): Long {
        return when (from) {
            CatalogType.AccountingObjects -> {
                accountingObjectRepository.getAccountingObjectsCount(
                    params = params,
                    selectedLocationIds = params.getFilterLocationIds(),
                    structuralIds = params.getFilterStructuralIds(),
                    showUtilized = params.getFilterShowUtilizedAccountingObjects()
                )
            }
            CatalogType.Employees -> {
                employeeRepository.getEmployeesCount(params = params)
            }
            CatalogType.Nomenclatures -> {
                nomenclatureRepository.getNomenclaturesCount(params = params)
            }
            is CatalogType.Documents -> {
                documentRepository.getDocumentsCount(
                    type = from.documentTypeDomain,
                    params = params
                )
            }
            CatalogType.Inventories -> {
                inventoriesRepository.getInventoriesCount(params = params)
            }
            CatalogType.Reserves -> {
                reservesRepository.getReservesFilterCount(
                    params = params,
                    selectedLocationIds = params.getFilterLocationIds(),
                    structuralIds = params.getFilterStructuralIds(),
                    hideZeroReserves = params.getFilterHideZeroReserves()
                )
            }
            else -> 0
        }
    }

    private suspend fun List<ParamDomain>.getFilterLocationIds(): List<String?>? {
        val lastLocationId = getFilterLocationLastId()
        return if (lastLocationId == null) {
            null
        } else {
            locationRepository.getAllLocationsIdsByParent(lastLocationId)
        }
    }

    private suspend fun List<ParamDomain>.getFilterStructuralIds(): List<String?>? {
        val lastStructuralId = getFilterStructuralLastId(ManualType.STRUCTURAL)
        return if (lastStructuralId == null) {
            null
        } else {
            structuralRepository.getAllStructuralsIdsByParent(lastStructuralId)
        }
    }

    companion object {
        private const val NO_POSITION = -1
    }
}
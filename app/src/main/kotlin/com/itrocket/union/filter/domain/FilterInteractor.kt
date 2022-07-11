package com.itrocket.union.filter.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.branches.domain.dependencies.BranchesRepository
import com.itrocket.union.departments.domain.dependencies.DepartmentRepository
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.employees.domain.dependencies.EmployeeRepository
import com.itrocket.union.filter.domain.dependencies.FilterRepository
import com.itrocket.union.filter.domain.entity.CatalogType
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.Params
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.nomenclature.domain.dependencies.NomenclatureRepository
import com.itrocket.union.regions.domain.dependencies.RegionRepository
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import kotlinx.coroutines.withContext

class FilterInteractor(
    private val repository: FilterRepository,
    private val accountingObjectRepository: AccountingObjectRepository,
    private val employeeRepository: EmployeeRepository,
    private val branchesRepository: BranchesRepository,
    private val departmentRepository: DepartmentRepository,
    private val nomenclatureRepository: NomenclatureRepository,
    private val regionRepository: RegionRepository,
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

    fun getDefaultTypeParams(params: Params): List<ParamDomain> {
        return params.paramList.filter {
            params.isDefaultParamType(it)
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

    suspend fun changeLocationFilter(
        filters: List<ParamDomain>,
        location: LocationParamDomain
    ): List<ParamDomain> {
        return withContext(coreDispatchers.io) {
            val mutableFilters = filters.toMutableList()
            val locationIndex = filters.indexOfFirst { it.type == ManualType.LOCATION }
            if (locationIndex != NO_POSITION) {
                mutableFilters[locationIndex] = location
            }
            mutableFilters
        }
    }

    suspend fun getResultCount(from: CatalogType?, params: List<ParamDomain>): Long {
        return when (from) {
            CatalogType.ACCOUNTING_OBJECTS -> {
                accountingObjectRepository.getAccountingObjectsCount(
                    params = params,
                    selectedLocationIds = params.getFilterLocationIds()
                )
            }
            CatalogType.EMPLOYEES -> {
                employeeRepository.getEmployeesCount(params = params)
            }
            CatalogType.BRANCHES -> {
                branchesRepository.getBranchesCount(params = params)
            }
            CatalogType.DEPARTMENTS -> {
                departmentRepository.getDepartmentsCount(params = params)
            }
            CatalogType.NOMENCLATURES -> {
                nomenclatureRepository.getNomenclaturesCount(params = params)
            }
            CatalogType.REGIONS -> {
                regionRepository.getRegionsCount()
            }
            CatalogType.DOCUMENTS -> {
                documentRepository.getAllDocumentsCount(params = params)
            }
            CatalogType.INVENTORIES -> {
                inventoriesRepository.getInventoriesCount(params = params)
            }
            CatalogType.RESERVES -> {
                reservesRepository.getReservesFilterCount(
                    params = params,
                    selectedLocationIds = params.getFilterLocationIds()
                )
            }
            else -> 0
        }
    }

    private suspend fun List<ParamDomain>.getFilterLocationIds(): List<String?> {
        return locationRepository.getAllLocationsIdsByParent(getFilterLocationLastId())
    }

    companion object {
        private const val NO_POSITION = -1
    }
}
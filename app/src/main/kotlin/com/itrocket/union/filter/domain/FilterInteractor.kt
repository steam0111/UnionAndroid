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
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.Params
import com.itrocket.union.nomenclature.domain.dependencies.NomenclatureRepository
import com.itrocket.union.regions.domain.dependencies.RegionRepository
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.firstOrNull

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
    private val reservesRepository: ReservesRepository
) {
    fun getFilters() = repository.getFilters()

    fun dropFilterFields(filters: List<ParamDomain>): List<ParamDomain> {
        return filters.toMutableList().map {
            it.copy(value = "", id = null)
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

    fun changeLocationFilter(
        filters: List<ParamDomain>,
        location: LocationParamDomain
    ): List<ParamDomain> {
        val mutableFilters = filters.toMutableList()
        val locationIndex = filters.indexOfFirst { it.type == ManualType.LOCATION }
        if (locationIndex != NO_POSITION) {
            mutableFilters[locationIndex] =
                (mutableFilters[locationIndex] as LocationParamDomain).copy(
                    values = location.values
                )
        }
        return mutableFilters
    }

    suspend fun getResultCount(from: CatalogType?, params: List<ParamDomain>): Long {
        return when (from) {
            CatalogType.ACCOUNTING_OBJECTS -> {
                accountingObjectRepository.getAccountingObjectsCount(params = params)
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
                reservesRepository.getReservesFilterCount(params = params)
            }
            else -> 0
        }
    }

    companion object {
        private const val NO_POSITION = -1
    }
}
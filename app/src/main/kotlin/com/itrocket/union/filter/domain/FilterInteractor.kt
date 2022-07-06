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

    //TODO переделать через базу данных
    suspend fun getResultCount(from: CatalogType?, params: List<ParamDomain>): Int {
        return when (from) {
            CatalogType.ACCOUNTING_OBJECTS -> {
                accountingObjectRepository.getAccountingObjects(params = params).count()
            }
            CatalogType.EMPLOYEES -> {
                employeeRepository.getEmployees(params = params).count()
            }
            CatalogType.BRANCHES -> {
                branchesRepository.getBranches(params = params).count()
            }
            CatalogType.DEPARTMENTS -> {
                departmentRepository.getDepartments(params = params).count()
            }
            CatalogType.NOMENCLATURES -> {
                nomenclatureRepository.getNomenclatures(params = params).count()
            }
            CatalogType.REGIONS -> {
                regionRepository.getRegions().count()
            }
            CatalogType.DOCUMENTS -> {
                documentRepository.getAllDocuments(params = params).firstOrNull()?.count() ?: 0
            }
            CatalogType.INVENTORIES -> {
                inventoriesRepository.getInventories(params = params).firstOrNull()?.count() ?: 0
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
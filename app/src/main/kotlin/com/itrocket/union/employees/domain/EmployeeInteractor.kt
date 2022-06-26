package com.itrocket.union.employees.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.employees.domain.dependencies.EmployeeRepository
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.withContext

class EmployeeInteractor(
    private val repository: EmployeeRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getEmployees(
        params: List<ParamDomain>?,
        searchQuery: String = ""
    ): List<EmployeeDomain> =
        withContext(coreDispatchers.io) {
            repository.getEmployees(params)
        }

    fun getFilters(): List<ParamDomain> {
        return listOf(
            ParamDomain(
                type = ManualType.ORGANIZATION,
                value = ""
            )
        )
    }
}
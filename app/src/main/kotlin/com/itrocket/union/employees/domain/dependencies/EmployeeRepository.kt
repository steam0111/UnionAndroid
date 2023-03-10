package com.itrocket.union.employees.domain.dependencies

import com.itrocket.union.employees.domain.entity.EmployeeDomain
import com.itrocket.union.manual.ParamDomain

interface EmployeeRepository {
    suspend fun getEmployees(
        textQuery: String? = null,
        params: List<ParamDomain>?,
        offset: Long?,
        limit: Long?
    ): List<EmployeeDomain>

    suspend fun getEmployeesCount(textQuery: String? = null, params: List<ParamDomain>?): Long
}
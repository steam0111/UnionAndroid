package com.itrocket.union.employees.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.employees.domain.dependencies.EmployeeRepository
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import kotlinx.coroutines.withContext

class EmployeeInteractor(
    private val repository: EmployeeRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getEmployees(): List<EmployeeDomain> = withContext(coreDispatchers.io) {
        repository.getEmployees()
    }
}
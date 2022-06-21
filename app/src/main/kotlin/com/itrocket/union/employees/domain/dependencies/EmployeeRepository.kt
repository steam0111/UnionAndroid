package com.itrocket.union.employees.domain.dependencies

import com.itrocket.union.employees.domain.entity.EmployeeDomain

interface EmployeeRepository {
    suspend fun getEmployees(): List<EmployeeDomain>
}
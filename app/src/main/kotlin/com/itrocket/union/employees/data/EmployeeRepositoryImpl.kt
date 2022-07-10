package com.itrocket.union.employees.data

import com.example.union_sync_api.data.EmployeeSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.employees.domain.dependencies.EmployeeRepository
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import kotlinx.coroutines.withContext
import com.itrocket.union.employees.data.mapper.map
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getOrganizationId

class EmployeeRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val employeeSyncApi: EmployeeSyncApi
) : EmployeeRepository {
    override suspend fun getEmployees(
        textQuery: String?,
        params: List<ParamDomain>?,
    ): List<EmployeeDomain> =
        withContext(coreDispatchers.io) {
            employeeSyncApi.getEmployees(
                organizationId = params?.getOrganizationId(),
                textQuery = textQuery
            ).map()
        }

    override suspend fun getEmployeesCount(textQuery: String?, params: List<ParamDomain>?): Long =
        withContext(coreDispatchers.io) {
            employeeSyncApi.getEmployeesCount(
                organizationId = params?.getOrganizationId(),
                textQuery = textQuery
            )
        }
}
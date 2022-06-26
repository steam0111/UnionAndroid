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
    override suspend fun getEmployees(params: List<ParamDomain>?): List<EmployeeDomain> =
        withContext(coreDispatchers.io) {
            employeeSyncApi.getEmployees(
                organizationId = params?.getOrganizationId()
            ).map()
        }
}
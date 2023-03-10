package com.itrocket.union.employees.data

import com.example.union_sync_api.data.EmployeeSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.employees.domain.dependencies.EmployeeRepository
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import kotlinx.coroutines.withContext
import com.itrocket.union.employees.data.mapper.map
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getFilterStructuralLastId

class EmployeeRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val employeeSyncApi: EmployeeSyncApi
) : EmployeeRepository {
    override suspend fun getEmployees(
        textQuery: String?,
        params: List<ParamDomain>?,
        offset: Long?,
        limit: Long?
    ): List<EmployeeDomain> =
        withContext(coreDispatchers.io) {
            employeeSyncApi.getEmployees(
                structuralId = params?.getFilterStructuralLastId(ManualType.STRUCTURAL),
                textQuery = textQuery,
                limit = limit,
                offset = offset
            ).map()
        }

    override suspend fun getEmployeesCount(textQuery: String?, params: List<ParamDomain>?): Long =
        withContext(coreDispatchers.io) {
            employeeSyncApi.getEmployeesCount(
                structuralId = params?.getFilterStructuralLastId(ManualType.STRUCTURAL),
                textQuery = textQuery
            )
        }
}
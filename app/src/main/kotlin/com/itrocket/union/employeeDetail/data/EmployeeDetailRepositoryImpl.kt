package com.itrocket.union.employeeDetail.data

import com.example.union_sync_api.data.EmployeeSyncApi
import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain
import com.itrocket.union.employeeDetail.data.mapper.toEmployeeDetailDomain
import com.itrocket.union.employeeDetail.domain.dependencies.EmployeeDetailRepository

class EmployeeDetailRepositoryImpl(
    private val syncApi: EmployeeSyncApi
) : EmployeeDetailRepository {

    override suspend fun getEmployeeDetail(id: String): EmployeeDetailDomain {
        return syncApi.getEmployeeDetail(id).toEmployeeDetailDomain()
    }
}

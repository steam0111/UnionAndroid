package com.itrocket.union.employeeDetail.data

import com.example.union_sync_api.data.EmployeeSyncApi
import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain
import com.itrocket.union.employeeDetail.data.mapper.toEmployeeDetailDomain
import com.itrocket.union.employeeDetail.domain.dependencies.EmployeeDetailRepository
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.structural.data.mapper.toStructuralDomain

class EmployeeDetailRepositoryImpl(
    private val syncApi: EmployeeSyncApi
) : EmployeeDetailRepository {

    override suspend fun getEmployeeDetail(id: String): EmployeeDetailDomain? {
        return syncApi.getEmployeeDetail(id)?.toEmployeeDetailDomain()
    }

    override suspend fun getEmployeeStructuralById(
        employeeId: String,
        structuralToParamDomain: StructuralParamDomain,
        needUpdate: Boolean
    ): StructuralParamDomain {
        val employee = syncApi.getEmployeeDetail(employeeId)
        return structuralToParamDomain.copy(
            needUpdate = needUpdate,
            structurals = employee?.structuralSyncEntities?.map { it.toStructuralDomain() }
                .orEmpty())
    }
}

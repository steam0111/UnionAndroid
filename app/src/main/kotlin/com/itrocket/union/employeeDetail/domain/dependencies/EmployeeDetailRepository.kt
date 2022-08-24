package com.itrocket.union.employeeDetail.domain.dependencies

import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain
import com.itrocket.union.manual.StructuralParamDomain

interface EmployeeDetailRepository {
    suspend fun getEmployeeDetail(id: String): EmployeeDetailDomain

    suspend fun getEmployeeStructuralById(employeeId: String, structuralToParamDomain: StructuralParamDomain) : StructuralParamDomain
}
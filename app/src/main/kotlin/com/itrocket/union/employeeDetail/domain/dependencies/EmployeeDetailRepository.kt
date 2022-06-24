package com.itrocket.union.employeeDetail.domain.dependencies

import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain

interface EmployeeDetailRepository {
    suspend fun getEmployeeDetail(id: String): EmployeeDetailDomain
}
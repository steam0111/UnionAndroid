package com.itrocket.union.employeeDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain
import com.itrocket.union.employeeDetail.domain.dependencies.EmployeeDetailRepository
import kotlinx.coroutines.withContext

class EmployeeDetailInteractor(
    private val repository: EmployeeDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getEmployeeDetail(id: String): EmployeeDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getEmployeeDetail(id)
        }
}
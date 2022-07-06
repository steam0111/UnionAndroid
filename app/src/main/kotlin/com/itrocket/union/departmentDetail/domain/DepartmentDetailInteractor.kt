package com.itrocket.union.departmentDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.departmentDetail.domain.dependencies.DepartmentDetailRepository
import com.itrocket.union.departmentDetail.domain.entity.DepartmentDetailDomain
import kotlinx.coroutines.withContext

class DepartmentDetailInteractor(
    private val repository: DepartmentDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getDepartmentDetail(id: String): DepartmentDetailDomain =
        withContext(coreDispatchers.io) {
            repository.getDepartmentById(id)
        }
}
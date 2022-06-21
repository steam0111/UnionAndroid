package com.itrocket.union.departments.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.departments.domain.dependencies.DepartmentRepository
import com.itrocket.union.departments.domain.entity.DepartmentDomain
import kotlinx.coroutines.withContext

class DepartmentInteractor(
    private val repository: DepartmentRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getDepartments(): List<DepartmentDomain> = withContext(coreDispatchers.io) {
        repository.getDepartments()
    }
}
package com.itrocket.union.departments.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.departments.domain.dependencies.DepartmentRepository
import com.itrocket.union.departments.domain.entity.DepartmentDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.withContext

class DepartmentInteractor(
    private val repository: DepartmentRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getDepartments(
        params: List<ParamDomain>?,
        searchQuery: String = ""
    ): List<DepartmentDomain> =
        withContext(coreDispatchers.io) {
            repository.getDepartments(params)
        }

    fun getFilters(): List<ParamDomain> {
        return listOf(
            ParamDomain(
                type = ManualType.ORGANIZATION,
                value = ""
            )
        )
    }
}
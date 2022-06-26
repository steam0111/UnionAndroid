package com.itrocket.union.departments.domain.dependencies

import com.itrocket.union.departments.domain.entity.DepartmentDomain
import com.itrocket.union.manual.ParamDomain

interface DepartmentRepository {
    suspend fun getDepartments(
        textQuery: String? = null,
        params: List<ParamDomain>?
    ): List<DepartmentDomain>
}
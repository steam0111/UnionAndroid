package com.itrocket.union.departments.domain.dependencies

import com.itrocket.union.departments.domain.entity.DepartmentDomain

interface DepartmentRepository {
    suspend fun getDepartments(): List<DepartmentDomain>
}
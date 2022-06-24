package com.itrocket.union.departmentDetail.domain.dependencies

import com.itrocket.union.departmentDetail.domain.entity.DepartmentDetailDomain

interface DepartmentDetailRepository {
    suspend fun getDepartmentById(id: String): DepartmentDetailDomain
}
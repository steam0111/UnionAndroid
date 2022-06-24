package com.itrocket.union.departmentDetail.data

import com.example.union_sync_api.data.DepartmentSyncApi
import com.itrocket.union.departmentDetail.data.mapper.toDomain
import com.itrocket.union.departmentDetail.domain.dependencies.DepartmentDetailRepository
import com.itrocket.union.departmentDetail.domain.entity.DepartmentDetailDomain

class DepartmentDetailRepositoryImpl(
    private val syncApi: DepartmentSyncApi
) : DepartmentDetailRepository {

    override suspend fun getDepartmentById(id: String): DepartmentDetailDomain {
        return syncApi.getDepartmentDetail(id).toDomain()
    }
}

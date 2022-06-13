package com.itrocket.union.departments.data

import com.example.union_sync_api.data.DepartmentSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.departments.domain.dependencies.DepartmentRepository
import com.itrocket.union.departments.domain.entity.DepartmentDomain
import kotlinx.coroutines.withContext
import com.itrocket.union.departments.data.mapper.map

class DepartmentRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val departmentSyncApi: DepartmentSyncApi
) : DepartmentRepository {

    override suspend fun getDepartments(): List<DepartmentDomain> =
        withContext(coreDispatchers.io) {
            departmentSyncApi.getDepartments().map()
        }
}
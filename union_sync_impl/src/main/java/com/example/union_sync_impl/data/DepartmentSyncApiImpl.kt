package com.example.union_sync_impl.data

import com.example.union_sync_api.data.DepartmentSyncApi
import com.example.union_sync_api.entity.DepartmentDetailSyncEntity
import com.example.union_sync_api.entity.DepartmentSyncEntity
import com.example.union_sync_impl.dao.DepartmentDao
import com.example.union_sync_impl.dao.sqlDepartmentsQuery
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity

class DepartmentSyncApiImpl(
    private val departmentDao: DepartmentDao,
) : DepartmentSyncApi {

    override suspend fun getDepartments(organizationId: String?): List<DepartmentSyncEntity> {
        val departmentDb = departmentDao.getAll(sqlDepartmentsQuery(organizationId))
        return departmentDb.map { it.toSyncEntity() }
    }

    override suspend fun getDepartmentDetail(id: String): DepartmentDetailSyncEntity {
        return departmentDao.getFullById(id).toDetailSyncEntity()
    }
}
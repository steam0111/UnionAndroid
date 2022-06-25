package com.example.union_sync_impl.data

import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity

class EmployeeSyncApiImpl(
    private val employeeDao: EmployeeDao
) : EmployeeSyncApi {
    override suspend fun getEmployees(): List<EmployeeSyncEntity> {
        val employeeDb = employeeDao.getAll()
        return employeeDb.map { it.toSyncEntity() }
    }

    override suspend fun getEmployeeDetail(id: String): EmployeeDetailSyncEntity {
        return employeeDao.getFullById(id).toDetailSyncEntity()
    }
}
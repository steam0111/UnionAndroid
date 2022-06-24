package com.example.union_sync_impl.data

import com.example.union_sync_api.data.EmployeeSyncApi
import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.data.mapper.toDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toEmployeeDb
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import org.openapitools.client.custom_api.EmployeeApi

class EmployeeSyncApiImpl(
    private val employeeApi: EmployeeApi,
    private val employeeDao: EmployeeDao,
    private val organizationDao: OrganizationDao
) : EmployeeSyncApi {
    override suspend fun getEmployees(): List<EmployeeSyncEntity> {
        val employeeDb = employeeDao.getAll()
        if (employeeDb.isEmpty()) {
            val employeeNetwork = employeeApi.apiCatalogsEmployeesGet().list ?: return emptyList()
            organizationDao.insertAll(employeeNetwork.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
            employeeDao.insertAll(employeeNetwork.map { it.toEmployeeDb() })
            return employeeDao.getAll().map { it.toSyncEntity() }
        }
        return employeeDb.map { it.toSyncEntity() }
    }

    override suspend fun getEmployeeDetail(id: String): EmployeeDetailSyncEntity {
        return employeeDao.getFullById(id).toDetailSyncEntity()
    }
}
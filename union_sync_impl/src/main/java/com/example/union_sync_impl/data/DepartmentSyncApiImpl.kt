package com.example.union_sync_impl.data

import com.example.union_sync_api.data.DepartmentSyncApi
import com.example.union_sync_api.entity.DepartmentSyncEntity
import com.example.union_sync_impl.dao.DepartmentDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.data.mapper.toDepartmentDb
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import org.openapitools.client.custom_api.DepartmentApi

class DepartmentSyncApiImpl(
    private val departmentApi: DepartmentApi,
    private val departmentDao: DepartmentDao,
    private val organizationDao: OrganizationDao
) : DepartmentSyncApi {

    override suspend fun getDepartments(): List<DepartmentSyncEntity> {
        val departmentDb = departmentDao.getAll()
        if (departmentDb.isEmpty()) {
            val departmentNetwork =
                departmentApi.apiCatalogsDepartmentGet().list ?: return emptyList()
            organizationDao.insertAll(departmentNetwork.mapNotNull { it.extendedOrganization?.toOrganizationDb() })
            departmentDao.insertAll(departmentNetwork.map { it.toDepartmentDb() })
            return departmentDao.getAll().map { it.toSyncEntity() }
        }
        return departmentDb.map { it.toSyncEntity() }
    }
}
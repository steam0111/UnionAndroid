package com.example.union_sync_api.data

import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity

interface EmployeeSyncApi {
    suspend fun getEmployees(organizationId: String? = null): List<EmployeeSyncEntity>

    suspend fun getEmployeeDetail(id: String): EmployeeDetailSyncEntity
}
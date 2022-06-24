package com.example.union_sync_api.data

import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity

interface EmployeeSyncApi {
    suspend fun getEmployees(): List<EmployeeSyncEntity>

    suspend fun getEmployeeDetail(id: String): EmployeeDetailSyncEntity
}
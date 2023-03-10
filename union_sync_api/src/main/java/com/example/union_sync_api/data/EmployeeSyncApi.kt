package com.example.union_sync_api.data

import com.example.union_sync_api.entity.EmployeeDetailSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity

interface EmployeeSyncApi {
    suspend fun getEmployees(
        textQuery: String? = null,
        structuralId: String? = null,
        offset: Long? = null,
        limit: Long? = null
    ): List<EmployeeSyncEntity>

    suspend fun getEmployeesCount(
        textQuery: String? = null,
        structuralId: String? = null
    ): Long

    suspend fun getEmployeeDetail(id: String): EmployeeDetailSyncEntity?
}
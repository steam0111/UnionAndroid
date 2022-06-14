package com.example.union_sync_api.data

import com.example.union_sync_api.entity.EmployeeSyncEntity

interface EmployeeSyncApi {
    suspend fun getEmployees(): List<EmployeeSyncEntity>
}
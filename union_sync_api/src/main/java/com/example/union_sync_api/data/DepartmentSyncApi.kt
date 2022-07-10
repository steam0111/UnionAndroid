package com.example.union_sync_api.data

import com.example.union_sync_api.entity.DepartmentDetailSyncEntity
import com.example.union_sync_api.entity.DepartmentSyncEntity

interface DepartmentSyncApi {
    suspend fun getDepartments(
        textQuery: String? = null,
        organizationId: String? = null
    ): List<DepartmentSyncEntity>

    suspend fun getDepartmentsCount(
        textQuery: String? = null,
        organizationId: String? = null
    ): Long

    suspend fun getDepartmentDetail(id: String): DepartmentDetailSyncEntity
}
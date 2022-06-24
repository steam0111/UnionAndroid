package com.example.union_sync_api.data

import com.example.union_sync_api.entity.DepartmentDetailSyncEntity
import com.example.union_sync_api.entity.DepartmentSyncEntity

interface DepartmentSyncApi {
    suspend fun getDepartments(): List<DepartmentSyncEntity>

    suspend fun getDepartmentDetail(id: String): DepartmentDetailSyncEntity
}
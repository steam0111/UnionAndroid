package com.example.union_sync_api.entity

data class DepartmentDetailSyncEntity(
    val department: DepartmentSyncEntity,
    val organization: OrganizationSyncEntity?,
)
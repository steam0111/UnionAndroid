package com.example.union_sync_api.entity

data class OrganizationDetailSyncEntity(
    val organization: OrganizationSyncEntity,
    val employee: EmployeeSyncEntity?
)
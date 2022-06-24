package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullDepartmentDb(
    @Embedded
    val departmentDb: DepartmentDb,
    @Embedded(prefix = "organizations_")
    val organizationDb: OrganizationDb?,
)
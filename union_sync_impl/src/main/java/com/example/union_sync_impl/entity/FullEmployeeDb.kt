package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullEmployeeDb(
    @Embedded
    val employeeDb: EmployeeDb,
    @Embedded(prefix = "organizations_")
    val organizationDb: OrganizationDb?,
)
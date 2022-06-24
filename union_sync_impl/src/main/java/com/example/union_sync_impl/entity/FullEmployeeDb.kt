package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullEmployeeDb(
    @Embedded
    val employeeDb: EmployeeDb,
    @Embedded(prefix = "organization")
    val organizationDb: OrganizationDb?,
)
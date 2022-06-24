package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullOrganizationDb(
    @Embedded
    val organizationDb: OrganizationDb,
    @Embedded(prefix = "employees_")
    val employeeDb: EmployeeDb?,
)
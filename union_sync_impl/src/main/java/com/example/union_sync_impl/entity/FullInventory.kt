package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullInventory(
    @Embedded
    val inventoryDb: InventoryDb,
    @Embedded(prefix = "organizations_")
    val organizationDb: OrganizationDb,
    @Embedded(prefix = "employees_")
    val employeeDb: EmployeeDb
)
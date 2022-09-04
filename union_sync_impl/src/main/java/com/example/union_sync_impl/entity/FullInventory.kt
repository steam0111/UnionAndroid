package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.structural.StructuralDb

class FullInventory(
    @Embedded
    val inventoryDb: InventoryDb,
    @Embedded(prefix = "employees_")
    val employeeDb: EmployeeDb?,
    @Embedded(prefix = "structural_")
    val structuralDb: StructuralDb?,
    @Embedded(prefix = "inventory_base_")
    val inventoryBaseDb: EnumDb?
)
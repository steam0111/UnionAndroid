package com.example.union_sync_impl.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_checker")
class InventoryCheckerDb(
    val inventoryId: String,
    val employeeId: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
)

class FullInventoryCheckerDb(
    @Embedded(prefix = "employee_")
    val employee: EmployeeDb,
    @Embedded
    val checker: InventoryCheckerDb
)
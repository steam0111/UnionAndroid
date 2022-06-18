package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventories")
class InventoryDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val organizationId: String,
    val employeeId: String,
    val accountingObjectsIds: List<String>,
    val date: String
)
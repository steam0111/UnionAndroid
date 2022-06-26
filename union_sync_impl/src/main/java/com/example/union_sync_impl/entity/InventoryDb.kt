package com.example.union_sync_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity

@Entity(tableName = "inventories")
class InventoryDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = "",
    val organizationId: String,
    val employeeId: String?,
    val accountingObjectsIds: List<AccountingObjectInfoSyncEntity>,
    val locationIds: List<String>? = listOf(),
    val date: Long
)
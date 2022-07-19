package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb
import com.squareup.moshi.Json

@Entity(tableName = "inventories")
class InventoryDb(
    id: String = "",
    updateDate: Long?,
    val organizationId: String?,
    val employeeId: String?,
    val locationIds: List<String>?,
    val date: Long?,
    val inventoryStatus: String,
    val code: String?,
    val name: String?
) : SyncItemDb(id, updateDate)
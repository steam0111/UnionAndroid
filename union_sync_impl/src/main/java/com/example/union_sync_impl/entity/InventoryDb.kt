package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb
import com.squareup.moshi.Json

@Entity(tableName = "inventories")
class InventoryDb(
    id: String = "",
    cancel: Boolean? = false,
    val employeeId: String?,
    val locationIds: List<String>?,
    val structuralId: String?,
    val inventoryBaseId: String?,
    val inventoryStatus: String,
    val code: String?,
    val name: String?,
    val creationDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : SyncItemDb(
    id = id,
    cancel = cancel,
    insertDate = creationDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)
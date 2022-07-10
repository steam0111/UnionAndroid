package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "inventory_record")
class InventoryRecordDb(
    id: String,
    val inventoryId: String,
    val accountingObjectId: String,
    val inventoryStatus: String,
    updateDate: Long
): SyncItemDb(id, updateDate)
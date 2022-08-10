package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

/*
* Создается для 1 инвентарной ведомсти и для каждого объекта учета
* */
@Entity(tableName = "inventory_record")
class InventoryRecordDb(
    id: String,
    val inventoryId: String,
    val accountingObjectId: String,
    val inventoryStatus: String,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : SyncItemDb(
    id = id,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
)
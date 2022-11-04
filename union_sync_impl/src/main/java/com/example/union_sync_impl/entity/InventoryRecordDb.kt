package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

/*
* Создается для 1 инвентарной ведомсти и для каждого объекта учета
* */
@Entity(tableName = "inventory_record")
class InventoryRecordDb(
    id: String,
    cancel: Boolean? = false,
    val inventoryId: String,
    val accountingObjectId: String,
    val inventoryStatus: String,
    insertDate: Long?,
    updateDate: Long?,
    userInserted: String?,
    userUpdated: String?,
) : SyncItemDb(
    id = id,
    cancel = cancel,
    insertDate = insertDate,
    updateDate = updateDate,
    userUpdated = userUpdated,
    userInserted = userInserted
) {
    fun copy(
        id: String = this.id,
        cancel: Boolean? = this.cancel,
        inventoryId: String = this.inventoryId,
        accountingObjectId: String = this.accountingObjectId,
        inventoryStatus: String = this.inventoryStatus,
        insertDate: Long? = this.insertDate,
        updateDate: Long? = this.updateDate,
        userInserted: String? = this.userInserted,
        userUpdated: String? = this.userUpdated
    ): InventoryRecordDb {
        return InventoryRecordDb(
            id = id,
            cancel = cancel,
            inventoryId = inventoryId,
            accountingObjectId = accountingObjectId,
            inventoryStatus = inventoryStatus,
            insertDate = insertDate,
            updateDate = updateDate,
            userInserted = userInserted,
            userUpdated = userUpdated,
        )
    }
}
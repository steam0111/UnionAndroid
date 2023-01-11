package com.example.union_sync_impl.entity

import androidx.room.Entity
import com.example.union_sync_impl.entity.core.SyncItemDb

@Entity(tableName = "inventory_nomenclature_record")
class InventoryNomenclatureRecordDb(
    id: String,
    cancel: Boolean? = false,
    val inventoryId: String,
    val nomenclatureId: String,
    val expectedCount: Long?,
    val actualCount: Long?,
    val consignment: String?,
    val bookKeepingInvoice: Long?,
    val price: String?,
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
){

    fun copy(
        id: String = this.id,
        cancel: Boolean? = this.cancel,
        inventoryId: String = this.inventoryId,
        insertDate: Long? = this.insertDate,
        updateDate: Long? = this.updateDate,
        userInserted: String? = this.userInserted,
        userUpdated: String? = this.userUpdated,
        actualCount: Long? = this.actualCount,
        nomenclatureId: String = this.nomenclatureId,
        expectedCount: Long? = this.expectedCount,
        consignment: String? = this.consignment,
        price: String? = this.price,
        bookKeepingInvoice: Long? = this.bookKeepingInvoice,
    ): InventoryNomenclatureRecordDb {
        return InventoryNomenclatureRecordDb(
            id = id,
            cancel = cancel,
            inventoryId = inventoryId,
            insertDate = insertDate,
            updateDate = updateDate,
            userInserted = userInserted,
            userUpdated = userUpdated,
            actualCount = actualCount,
            nomenclatureId = nomenclatureId,
            expectedCount = expectedCount,
            consignment = consignment,
            price = price,
            bookKeepingInvoice = bookKeepingInvoice,
        )
    }
}
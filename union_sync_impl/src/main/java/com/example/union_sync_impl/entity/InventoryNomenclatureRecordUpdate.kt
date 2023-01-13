package com.example.union_sync_impl.entity

import androidx.room.Entity
import java.math.BigDecimal

@Entity
data class InventoryNomenclatureRecordUpdate(
    val id: String,
    val inventoryId: String,
    val nomenclatureId: String,
    val updateDate: Long?,
    val userUpdated: String?,
    val expectedCount: Long?,
    val actualCount: Long?,
    val consignment: String?,
    val bookKeepingInvoice: BigDecimal?,
    val price: String?,
    val cancel: Boolean?
)
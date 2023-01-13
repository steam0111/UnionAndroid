package com.itrocket.union.inventory.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.InventoryNomenclatureRecordSyncEntity
import java.math.BigDecimal
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryNomenclatureDomain(
    val id: String,
    val nomenclatureId: String,
    val updateDate: Long?,
    val expectedCount: Long?,
    val actualCount: Long?,
    val consignment: String?,
    val bookKeepingInvoice: BigDecimal?,
    val price: String?,
    val cancel: Boolean?,
) : Parcelable

fun InventoryNomenclatureDomain.toSyncEntity(inventoryId: String) =
    InventoryNomenclatureRecordSyncEntity(
        id = id,
        inventoryId = inventoryId,
        nomenclatureId = nomenclatureId,
        updateDate = updateDate,
        expectedCount = expectedCount,
        actualCount = actualCount,
        consignment = consignment,
        bookKeepingInvoice = bookKeepingInvoice,
        price = price,
        cancel = cancel,
    )
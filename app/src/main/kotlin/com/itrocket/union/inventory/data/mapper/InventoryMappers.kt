package com.itrocket.union.inventory.data.mapper

import com.example.union_sync_api.entity.InventoryNomenclatureRecordSyncEntity
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.entity.InventoryDomain
import com.itrocket.union.inventory.domain.entity.InventoryNomenclatureDomain
import com.itrocket.union.inventoryContainer.domain.InventoryContainerType

fun List<InventoryNomenclatureRecordSyncEntity>.map(): List<InventoryNomenclatureDomain> = map {
    InventoryNomenclatureDomain(
        id = it.id,
        nomenclatureId = it.nomenclatureId,
        updateDate = it.updateDate,
        expectedCount = it.expectedCount,
        actualCount = it.actualCount,
        consignment = it.consignment,
        bookKeepingInvoice = it.bookKeepingInvoice,
        price = it.price,
        cancel = it.cancel,
        nomenclatureName = it.nomenclatureName,
        userInserted = it.userInserted,
        insertDate = it.insertDate
    )
}

fun InventoryStatus.toInventoryContainerType() = when (this) {
    InventoryStatus.CREATED -> InventoryContainerType.INVENTORY
    InventoryStatus.COMPLETED, InventoryStatus.IN_PROGRESS -> InventoryContainerType.INVENTORY_CREATE
}
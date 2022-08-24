package com.itrocket.union.inventory.data.mapper

import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.entity.InventoryDomain
import com.itrocket.union.inventoryContainer.domain.InventoryContainerType

fun List<Any>.map(): List<InventoryDomain> = map {
    InventoryDomain()
}

fun InventoryStatus.toInventoryContainerType() = when (this) {
    InventoryStatus.CREATED -> InventoryContainerType.INVENTORY
    InventoryStatus.COMPLETED, InventoryStatus.IN_PROGRESS -> InventoryContainerType.INVENTORY_CREATE
}
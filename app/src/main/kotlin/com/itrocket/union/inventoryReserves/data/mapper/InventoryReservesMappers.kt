package com.itrocket.union.inventoryReserves.data.mapper

import com.itrocket.union.inventoryReserves.domain.entity.InventoryReservesDomain

fun List<Any>.map(): List<InventoryReservesDomain> = map {
    InventoryReservesDomain()
}
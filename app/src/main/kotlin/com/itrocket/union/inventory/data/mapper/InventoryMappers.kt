package com.itrocket.union.inventory.data.mapper

import com.itrocket.union.inventory.domain.entity.InventoryDomain

fun List<Any>.map(): List<InventoryDomain> = map {
    InventoryDomain()
}
package com.itrocket.union.inventoryContainer.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class InventoryContainerType : Parcelable {
    INVENTORY_CREATE, INVENTORY
}
package com.itrocket.union.inventoryContainer.presentation.store

import android.os.Parcelable
import com.itrocket.union.inventoryContainer.domain.InventoryContainerType
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InventoryContainerArguments(
    val inventoryCreateDomain: InventoryCreateDomain?,
    val type: InventoryContainerType
) :
    Parcelable
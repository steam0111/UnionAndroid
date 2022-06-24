package com.itrocket.union.inventoryContainer.presentation.store

import android.os.Parcelable
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InventoryContainerArguments(val inventoryCreateDomain: InventoryCreateDomain) :
    Parcelable
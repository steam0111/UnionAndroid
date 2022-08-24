package com.itrocket.union.inventory.presentation.store

import android.os.Parcelable
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryArguments(val inventoryCreateDomain: InventoryCreateDomain?) : Parcelable
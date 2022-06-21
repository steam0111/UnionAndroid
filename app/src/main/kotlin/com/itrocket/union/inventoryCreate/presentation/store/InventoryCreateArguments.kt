package com.itrocket.union.inventoryCreate.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InventoryCreateArguments(
    val inventoryDocument: InventoryCreateDomain
) : Parcelable
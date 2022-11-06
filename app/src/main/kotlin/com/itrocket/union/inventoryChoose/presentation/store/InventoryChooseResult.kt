package com.itrocket.union.inventoryChoose.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventoryChoose.domain.InventoryChooseActionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryChooseResult(
    val type: InventoryChooseActionType,
    val accountingObject: AccountingObjectDomain
) : Parcelable
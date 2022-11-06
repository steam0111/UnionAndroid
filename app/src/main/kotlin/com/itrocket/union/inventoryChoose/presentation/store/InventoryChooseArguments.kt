package com.itrocket.union.inventoryChoose.presentation.store

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryChooseArguments(val accountingObject: AccountingObjectDomain) : Parcelable
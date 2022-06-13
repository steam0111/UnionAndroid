package com.itrocket.union.inventoryCreate.domain.entity

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.utils.getTextDateFromStringDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryCreateDomain(
    val number: String,
    val date: String,
    val time: String,
    val documentInfo: List<String>
) : Parcelable {

    fun getTextDate() = getTextDateFromStringDate(date)
}
package com.itrocket.union.inventoryCreate.domain.entity

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.utils.getTextDateFromStringDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryCreateDomain(
    val number: String,
    val date: String,
    val time: String,
    val inventoryStatus: InventoryStatus = InventoryStatus.CREATED,
    val documentInfo: List<String>,
    val accountingObjectList: List<AccountingObjectDomain>
) : Parcelable {

    fun getTextDate() = getTextDateFromStringDate(date)
}
package com.itrocket.union.accountingObjects.domain.entity

import android.os.Parcelable
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import kotlinx.parcelize.Parcelize

private const val MAX_SHORT_INFO_LIST = 11

@Parcelize
data class AccountingObjectDomain(
    val id: String,
    val title: String,
    val status: ObjectStatus?,
    val inventoryStatus: InventoryAccountingObjectStatus = InventoryAccountingObjectStatus.NOT_FOUND,
    val isBarcode: Boolean = false,
    val listMainInfo: List<ObjectInfoDomain>,
    val listAdditionallyInfo: List<ObjectInfoDomain>
) : Parcelable {
    fun getShortMainInfoList() = listMainInfo.take(MAX_SHORT_INFO_LIST)
    fun getShortAdditionallyInfoList() = listAdditionallyInfo.take(MAX_SHORT_INFO_LIST)
}

@Parcelize
data class ObjectInfoDomain(val title: Int, val value: String) : Parcelable

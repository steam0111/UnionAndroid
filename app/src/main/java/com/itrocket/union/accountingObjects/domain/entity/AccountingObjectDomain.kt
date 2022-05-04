package com.itrocket.union.accountingObjects.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

private const val MAX_SHORT_INFO_LIST = 6

@Parcelize
data class AccountingObjectDomain(
    val id: String,
    val title: String,
    val status: ObjectStatus,
    val isBarcode: Boolean = false,
    val listInfo: List<ObjectInfoDomain>,
    val maxItemsCount: Int = 50
) : Parcelable {
    fun getShortInfoList() = listInfo.take(MAX_SHORT_INFO_LIST)
}

@Parcelize
data class ObjectInfoDomain(val title: String, val value: String) : Parcelable

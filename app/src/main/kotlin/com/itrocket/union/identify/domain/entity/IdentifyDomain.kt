package com.itrocket.union.identify.domain.entity

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import kotlinx.parcelize.Parcelize

private const val MAX_SHORT_INFO_LIST = 11

@Parcelize
data class IdentifyDomain(
    val id: String,
    val title: String,
    val status: ObjectStatus,
    val isBarcode: Boolean = false,
    val listMainInfo: List<ObjectInfoDomain>,
    val listAdditionallyInfo: List<ObjectInfoDomain>,
    val maxItemsCount: Int = 50
) : Parcelable {
    fun getShortMainInfoList() = listMainInfo.take(MAX_SHORT_INFO_LIST)
    fun getShortAdditionallyInfoList() = listAdditionallyInfo.take(MAX_SHORT_INFO_LIST)
}

@Parcelize
data class ObjectInfoDomain(val title: String, val value: String) : Parcelable

abstract class OSandReserves(
    open val id: String,
    open val title: String,
)

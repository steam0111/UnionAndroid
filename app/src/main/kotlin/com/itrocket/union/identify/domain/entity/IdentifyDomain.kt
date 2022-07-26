package com.itrocket.union.identify.domain.entity

import android.os.Parcelable
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.reserves.domain.entity.ReservesDomain
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
    val maxItemsCount: Int = 50,
    val accountingObjects: List<AccountingObjectDomain> = listOf(),
    val reserves: List<ReservesDomain> = listOf(),
) : Parcelable {
    fun getShortMainInfoList() = listMainInfo.take(MAX_SHORT_INFO_LIST)
    fun getShortAdditionallyInfoList() = listAdditionallyInfo.take(MAX_SHORT_INFO_LIST)
    val isDocumentExists: Boolean
        get() = id != null
}

@Parcelize
data class ObjectInfoDomain(val title: String, val value: String) : Parcelable

abstract class OSandReserves(
    open val id: String,
    open val title: String,
)

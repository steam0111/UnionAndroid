package com.itrocket.union.accountingObjects.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import kotlinx.parcelize.Parcelize

private const val MAX_SHORT_INFO_LIST = 5

@Parcelize
data class AccountingObjectDomain(
    val id: String,
    val title: String,
    val status: ObjectStatus?,
    val inventoryStatus: InventoryAccountingObjectStatus = InventoryAccountingObjectStatus.NOT_FOUND,
    val isBarcode: Boolean = false,
    val rfidValue: String?,
    val barcodeValue: String?,
    val listMainInfo: List<ObjectInfoDomain>,
    val listAdditionallyInfo: List<ObjectInfoDomain>
) : Parcelable {
    fun getShortMainInfoList() = listMainInfo.take(MAX_SHORT_INFO_LIST)
    fun getShortAdditionallyInfoList() = listAdditionallyInfo.take(MAX_SHORT_INFO_LIST)
}

@Parcelize
data class ObjectInfoDomain(
    val title: Int,
    val value: String? = null,
    val valueRes: Int? = null
) : Parcelable

fun AccountingObjectDomain.toAccountingObjectIdSyncEntity() =
    AccountingObjectInfoSyncEntity(id = id, status = inventoryStatus.name)

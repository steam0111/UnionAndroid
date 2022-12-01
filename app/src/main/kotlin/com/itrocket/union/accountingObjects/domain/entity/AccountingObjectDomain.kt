package com.itrocket.union.accountingObjects.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity
import com.itrocket.union.image.domain.ImageDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class AccountingObjectDomain(
    val id: String,
    val title: String,
    val status: ObjectStatus?,
    val inventoryStatus: InventoryAccountingObjectStatus = InventoryAccountingObjectStatus.NOT_FOUND,
    val isBarcode: Boolean = false,
    val rfidValue: String?,
    val barcodeValue: String?,
    val factoryNumber: String?,
    val listMainInfo: List<ObjectInfoDomain>,
    val listAdditionallyInfo: List<ObjectInfoDomain>,
    val characteristics: List<ObjectInfoDomain>,
    val inventoryNumber: String? = null,
    val comment: String? = null,
    val manualInput: Boolean? = null,
    val marked: Boolean,
    val forWrittenOff: Boolean? = null,
    val exploitingId: String? = null,
) : Parcelable {
    val hasBarcode: Boolean
        get() = !barcodeValue.isNullOrEmpty()

    val hasRfid: Boolean
        get() = !rfidValue.isNullOrEmpty()

    val isWrittenOff: Boolean
        get() = status?.type?.type == AccountingObjectStatus.WRITTEN_OFF.name
}

@Parcelize
data class ObjectInfoDomain(
    val title: Int? = null,
    val value: String? = null,
    val valueRes: Int? = null,
    val name: String? = null,
    val filedType: ObjectInfoType = ObjectInfoType.OTHER
) : Parcelable

fun AccountingObjectDomain.toAccountingObjectIdSyncEntity() =
    AccountingObjectInfoSyncEntity(
        id = id,
        status = inventoryStatus.name,
        comment = comment,
        manualInput = manualInput
    )

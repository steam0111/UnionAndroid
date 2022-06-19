package com.itrocket.union.inventoryCreate.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.utils.getStringDateFromMillis
import com.itrocket.union.utils.getTimeFromMillis
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryCreateDomain(
    val number: String,
    val date: Long,
    val inventoryStatus: InventoryStatus = InventoryStatus.CREATED,
    val documentInfo: List<ParamDomain>,
    val accountingObjects: List<AccountingObjectDomain>,
) : Parcelable {

    fun getTextDate() = getStringDateFromMillis(date)

    fun getTextTime() = getTimeFromMillis(date)
}

fun InventoryCreateDomain.toCreateSyncEntity(): InventoryCreateSyncEntity {
    val organizationId = documentInfo.find { it.type == ManualType.ORGANIZATION }?.id
    val molId = documentInfo.find { it.type == ManualType.MOL }?.id
    return InventoryCreateSyncEntity(
        organizationId = organizationId.orEmpty(),
        employeeId = molId.orEmpty(),
        accountingObjectsIds = accountingObjects.map { it.id }
    )
}

fun InventoryCreateDomain.toUpdateSyncEntity(): InventoryUpdateSyncEntity {
    val organizationId = documentInfo.find { it.type == ManualType.ORGANIZATION }?.id
    val molId = documentInfo.find { it.type == ManualType.MOL }?.id
    return InventoryUpdateSyncEntity(
        id = number.toLong(),
        organizationId = organizationId.orEmpty(),
        employeeId = molId.orEmpty(),
        accountingObjectsIds = accountingObjects.map { it.id },
        date = date
    )
}
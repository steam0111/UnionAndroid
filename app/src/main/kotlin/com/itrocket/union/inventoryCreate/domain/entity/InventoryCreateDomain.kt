package com.itrocket.union.inventoryCreate.domain.entity

import android.os.Parcelable
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.toAccountingObjectIdSyncEntity
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getFilterInventoryBaseId
import com.itrocket.union.manual.getFilterLocationIds
import com.itrocket.union.manual.getFilterStructuralLastId
import com.itrocket.union.manual.getMolInDepartmentId
import com.itrocket.union.utils.getStringDateFromMillis
import com.itrocket.union.utils.getTimeFromMillis
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryCreateDomain(
    val id: String?,
    val number: String,
    val creationDate: Long?,
    val inventoryStatus: InventoryStatus,
    val documentInfo: List<ParamDomain>,
    val accountingObjects: List<AccountingObjectDomain>,
    val userInserted: String?,
    val userUpdated: String?
) : Parcelable {

    fun getTextDate() = getStringDateFromMillis(creationDate)

    fun getTextTime() = getTimeFromMillis(creationDate)
}

fun InventoryCreateDomain.toUpdateSyncEntity(): InventoryUpdateSyncEntity {
    val structuralId = documentInfo.getFilterStructuralLastId(ManualType.STRUCTURAL)
    val molId = documentInfo.getMolInDepartmentId()
    val locationIds = documentInfo.getFilterLocationIds(ManualType.LOCATION_INVENTORY)

    return InventoryUpdateSyncEntity(
        id = id.orEmpty(),
        structuralId = structuralId,
        employeeId = molId,
        accountingObjectsIds = accountingObjects.map { it.toAccountingObjectIdSyncEntity() },
        creationDate = creationDate,
        locationIds = locationIds,
        inventoryStatus = this.inventoryStatus.name,
        code = number,
        userInserted = userInserted,
        userUpdated = userUpdated,
        inventoryBaseId = documentInfo.getFilterInventoryBaseId(),
        nomenclatureRecords = listOf() //TODO: Исправить в следующих подзадачах
    )
}
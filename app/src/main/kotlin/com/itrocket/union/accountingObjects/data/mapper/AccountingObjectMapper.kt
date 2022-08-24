package com.itrocket.union.accountingObjects.data.mapper

import com.example.union_sync_api.entity.AccountingObjectStatusSyncEntity
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.*
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus

fun List<AccountingObjectSyncEntity>.map(): List<AccountingObjectDomain> = map { syncEntity ->
    syncEntity.map()
}

fun AccountingObjectSyncEntity.map(): AccountingObjectDomain {
    val listMainInfo = mutableListOf<ObjectInfoDomain>()
    factoryNumber?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_factory_number, it))
    }
    inventoryNumber?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_inventory_number, it))
    }
    locationSyncEntity?.lastOrNull()?.name?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_location, it))
    }

    val status = inventoryStatus
    val inventoryStatus = if (!status.isNullOrBlank()) {
        InventoryAccountingObjectStatus.valueOf(status)
    } else {
        InventoryAccountingObjectStatus.NOT_FOUND
    }

    return AccountingObjectDomain(
        id = id,
        title = name,
        status = this.status?.toDomainStatus(),
        isBarcode = barcodeValue != null,
        listMainInfo = listMainInfo,
        listAdditionallyInfo = emptyList(),
        inventoryStatus = inventoryStatus,
        barcodeValue = barcodeValue,
        rfidValue = rfidValue,
        factoryNumber = factoryNumber
    )
}

fun AccountingObjectStatusSyncEntity.toDomainStatus(): ObjectStatus =
    ObjectStatus(
        text = name.orEmpty(),
        type = ObjectStatusType.valueOf(id)
    )

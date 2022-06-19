package com.itrocket.union.accountingObjects.data.mapper

import com.example.union_sync_api.entity.AccountingObjectStatus
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.*

fun List<AccountingObjectSyncEntity>.map(): List<AccountingObjectDomain> = map { syncEntity ->
    val listMainInfo = mutableListOf<ObjectInfoDomain>()
    syncEntity.factoryNumber?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_factory_number, it))
    }
    syncEntity.inventoryNumber?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_inventory_number, it))
    }
    syncEntity.locationSyncEntity?.name?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_location, it))
    }

    AccountingObjectDomain(
        id = syncEntity.id,
        title = syncEntity.name,
        status = syncEntity.status?.toDomainStatus(),
        isBarcode = syncEntity.barcodeValue != null,
        listMainInfo = listMainInfo,
        listAdditionallyInfo = emptyList()
    )

}

fun AccountingObjectStatus.toDomainStatus(): ObjectStatus =
    ObjectStatus(
        text = name,
        type = ObjectStatusType.valueOf(id)
    )

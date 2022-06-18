package com.itrocket.union.accountingObjects.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus

fun List<AccountingObjectSyncEntity>.map(): List<AccountingObjectDomain> = map {
    AccountingObjectDomain(
        id = it.id,
        title = it.name,
        status = ObjectStatus.AVAILABLE,
        isBarcode = it.barcodeValue != null,
        listMainInfo = emptyList(),
        listAdditionallyInfo = emptyList()
    )
}
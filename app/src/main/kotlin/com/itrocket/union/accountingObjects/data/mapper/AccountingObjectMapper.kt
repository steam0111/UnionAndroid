package com.itrocket.union.accountingObjects.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.EnumSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.ui.blue6
import com.itrocket.union.ui.burntSienna
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite5
import com.itrocket.union.ui.graphite6
import com.itrocket.union.ui.green7
import com.itrocket.union.ui.violet5
import com.itrocket.union.ui.white

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
        factoryNumber = factoryNumber,
        inventoryNumber = inventoryNumber,
        comment = comment,
        manualInput = manualInput,
        marked = marked,
        exploitingId = exploitingEmployeeId,
        forWrittenOff = forWriteOff
    )
}

fun EnumSyncEntity.toDomainStatus(): ObjectStatus {
    return ObjectStatus(
        text = name,
        type = this.toObjectStatusType()
    )
}

fun EnumSyncEntity.toObjectStatusType(): ObjectStatusType {
    var backgroundColor = graphite2
    var textColor = white
    when (id) {
        AccountingObjectStatus.AVAILABLE.name -> {
            backgroundColor = green7
        }
        AccountingObjectStatus.GIVEN.name -> {
            backgroundColor = blue6
        }
        AccountingObjectStatus.REVIEW.name -> {
            backgroundColor = burntSienna
        }
        AccountingObjectStatus.REPAIR.name -> {
            backgroundColor = violet5
        }
        AccountingObjectStatus.DECOMMISSIONED.name -> {
            backgroundColor = blue6
        }
        AccountingObjectStatus.WRITTEN_OFF.name -> {
            backgroundColor = graphite5
        }
        AccountingObjectStatus.TRANSIT.name -> {
            textColor = graphite6
        }
    }
    return ObjectStatusType(id, backgroundColor.value.toString(), textColor.value.toString(), name)
}


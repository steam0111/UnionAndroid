package com.itrocket.union.reserves.data.mapper

import com.example.union_sync_api.entity.ReserveSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import java.math.BigDecimal

fun List<ReserveSyncEntity>.map() = map {
    it.map()
}

fun ReserveSyncEntity.map(): ReservesDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()

    receptionDocumentNumber?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_reception_document_number, it))
    }

    unitPrice?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_unit_price, it))
    }

    locationSyncEntity?.lastOrNull()?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.accounting_objects_location, it))
    }

    labelType?.let {
        listInfo.add(
            ObjectInfoDomain(
                title = R.string.label_type_title,
                value = it.name,
            )
        )
    }

    nomenclatureSyncEntity?.let {
        listInfo.add(
            ObjectInfoDomain(
                title = R.string.reserves_detail_nomenclature_code,
                value = it.code,
            )
        )
    }

    return ReservesDomain(
        id = id,
        isBarcode = false,
        itemsCount = count ?: BigDecimal.ZERO,
        title = name,
        listInfo = listInfo,
        barcodeValue = barcodeValue,
        nomenclatureId = nomenclatureId,
        labelTypeId = labelTypeId,
        consignment = consignment,
        unitPrice = unitPrice,
        bookKeepingInvoice = bookkeepingInvoice
    )
}
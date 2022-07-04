package com.itrocket.union.reserves.data.mapper

import android.util.Log
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

    locationSyncEntity?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.accounting_objects_location, it))
    }

    return ReservesDomain(
        id = id,
        isBarcode = false,
        itemsCount = count ?: 0L,
        title = name,
        listInfo = listInfo
    )
}
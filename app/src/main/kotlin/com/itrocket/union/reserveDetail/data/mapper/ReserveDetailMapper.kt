package com.itrocket.union.reserveDetail.data.mapper

import com.example.union_sync_api.entity.ReserveDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain

fun ReserveDetailSyncEntity.map(): ReservesDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()

    reserveSyncEntity.receptionDocumentNumber?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_reception_document_number, it))
    }

    reserveSyncEntity.unitPrice?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_unit_price, it))
    }

    reserveSyncEntity.count?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_count, it.toString()))
    }

    locationSyncEntity?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.accounting_objects_location, it))
    }

    molSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.manual_mol, it.fullName))
    }

    structuralSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.manual_structural, it.name))
    }

    nomenclatureSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_nomenclature, it.name))
    }

    nomenclatureGroupSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_nomenclature_group, it.name))
    }

    orderSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_order, it.number))
    }

    receptionItemCategorySyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_reception_item_category, it.name))
    }

    return ReservesDomain(
        id = reserveSyncEntity.id,
        isBarcode = false,
        itemsCount = reserveSyncEntity.count ?: 0L,
        title = reserveSyncEntity.name,
        listInfo = listInfo
    )
}
package com.itrocket.union.reserveDetail.data.mapper

import com.example.union_sync_api.entity.ReserveDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.data.mapper.getStringBy
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.utils.getStringDateFromMillis

fun ReserveDetailSyncEntity.map(): ReservesDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()

    listInfo.add(ObjectInfoDomain(R.string.common_name, reserveSyncEntity.name))

    reserveSyncEntity.subName?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_sub_name, it))
    }

    reserveSyncEntity.count?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_count, it.toString()))
    }

    structuralSyncEntities?.let {
        listInfo.add(ObjectInfoDomain(R.string.manual_structural, it.joinToString(", ") { it.name }))
    }

    balanceUnitSyncEntities?.let {
        listInfo.add(ObjectInfoDomain(R.string.balance_unit, it.joinToString(", ") { it.name }))
    }

    listInfo.add(
        ObjectInfoDomain(
            R.string.accounting_object_traceable,
            valueRes = getStringBy(reserveSyncEntity.traceable)
        )
    )

    reserveSyncEntity.invoiceNumber?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_invoice_number, it))
    }

    molSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.accounting_object_mol, it.fullName))
    }

    locationSyncEntity?.name?.let {
        listInfo.add(ObjectInfoDomain(R.string.accounting_objects_location, it))
    }

    nomenclatureSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_nomenclature, it.name))
    }

    orderSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_order, it.number))
    }

    receptionItemCategorySyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_reception_item_category, it.name))
    }

    reserveSyncEntity.insertDate?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_date_create, getStringDateFromMillis(it)))
    }
    reserveSyncEntity.userInserted?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_user_create, it))
    }
    reserveSyncEntity.updateDate?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_date_update, getStringDateFromMillis(it)))
    }
    reserveSyncEntity.userUpdated?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_user_update, it))
    }

    return ReservesDomain(
        id = reserveSyncEntity.id,
        isBarcode = false,
        itemsCount = reserveSyncEntity.count ?: 0L,
        title = reserveSyncEntity.name,
        listInfo = listInfo
    )
}
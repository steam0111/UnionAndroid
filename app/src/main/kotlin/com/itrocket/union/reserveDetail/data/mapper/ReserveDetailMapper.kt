package com.itrocket.union.reserveDetail.data.mapper

import com.example.union_sync_api.entity.LabelType
import com.example.union_sync_api.entity.ReserveDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.data.mapper.getStringBy
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoBehavior
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.utils.getStringDateFromMillis

fun ReserveDetailSyncEntity.map(
    canReadLabelType: Boolean,
    canUpdateLabelType: Boolean
): ReservesDomain {
    val listInfo = mutableListOf<ObjectInfoDomain>()

    listInfo.add(ObjectInfoDomain(R.string.common_name, reserveSyncEntity.name))

    reserveSyncEntity.subName?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_sub_name, it))
    }

    reserveSyncEntity.count?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_count, it.toString()))
    }

    structuralSyncEntities?.let {
        listInfo.add(
            ObjectInfoDomain(
                R.string.manual_structural,
                it.joinToString(", ") { it.name })
        )
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

    listInfo.add(
        ObjectInfoDomain(
            title = R.string.common_barcode,
            value = reserveSyncEntity.barcodeValue
        )
    )

    reserveSyncEntity.invoiceNumber?.let {
        listInfo.add(ObjectInfoDomain(R.string.common_invoice_number, it))
    }

    molSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.accounting_object_mol, it.fullName))
    }

    locationSyncEntity?.let {
        listInfo.add(
            ObjectInfoDomain(
                R.string.accounting_objects_location,
                it.joinToString(", ") { it.name })
        )
    }

    nomenclatureSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_nomenclature, it.name))
    }

    nomenclatureSyncEntity?.let {
        listInfo.add(ObjectInfoDomain(R.string.reserves_detail_nomenclature_code, it.code))
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

    if (canReadLabelType) {
        listInfo.add(
            ObjectInfoDomain(
                title = R.string.accounting_objects_label_type,
                value = labelType?.name,
                valueRes = R.string.value_not_defined,
                fieldBehavior = ObjectInfoBehavior.LABEL_TYPE,
                canEdit = canUpdateLabelType
            )
        )
    }

    return ReservesDomain(
        id = reserveSyncEntity.id,
        isBarcode = false,
        itemsCount = reserveSyncEntity.count ?: 0L,
        title = reserveSyncEntity.name,
        listInfo = listInfo,
        barcodeValue = reserveSyncEntity.barcodeValue
    )
}

fun ReservesDomain.toLabelType(labelTypeId: String) = LabelType(id = id, labelTypeId = labelTypeId)

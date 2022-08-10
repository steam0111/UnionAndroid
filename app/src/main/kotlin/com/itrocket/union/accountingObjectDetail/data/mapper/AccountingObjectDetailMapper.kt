package com.itrocket.union.accountingObjectDetail.data.mapper

import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.data.mapper.toDomainStatus
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.utils.getTextDateFromStringDate


fun AccountingObjectDetailSyncEntity.toAccountingObjectDetailDomain(): AccountingObjectDomain {
    val listMainInfo = buildList {
        add(ObjectInfoDomain(R.string.common_name, accountingObject.name))
        accountingObject.subName?.let {
            add(ObjectInfoDomain(R.string.common_sub_name, it))
        }
        accountingObject.code?.let {
            add(ObjectInfoDomain(R.string.common_code, it))
        }
        accountingObject.factoryNumber?.let {
            add(ObjectInfoDomain(R.string.accounting_objects_factory_num, it))
        }

        accountingObject.inventoryNumber?.let {
            add(ObjectInfoDomain(R.string.accounting_objects_inventory_num, it))
        }

        structuralSyncEntity?.let {
            add(ObjectInfoDomain(R.string.manual_structural, it.name))
        }

        balanceUnitSyncEntity?.let {
            add(ObjectInfoDomain(R.string.balance_unit, it.name))
        }

        location?.name?.let {
            add(ObjectInfoDomain(R.string.accounting_objects_location, it))
        }

        accountingObject.status?.name?.let {
            add(ObjectInfoDomain(R.string.accounting_objects_current_status, it))
        }

        mol?.let {
            add(ObjectInfoDomain(R.string.accounting_object_mol, it.fullName))
        }
        exploitingEmployee?.let {
            add(ObjectInfoDomain(R.string.manual_exploiting, it.fullName))
        }

        accountingObject.status?.name?.let {
            add(ObjectInfoDomain(R.string.accounting_objects_current_status, it))
        }

        add(
            ObjectInfoDomain(
                R.string.accounting_object_marked,
                valueRes = getStringBy(accountingObject.marked)
            )
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_object_for_write_off,
                valueRes = getStringBy(accountingObject.forWriteOff)
            )
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_object_written_off,
                valueRes = getStringBy(accountingObject.writtenOff)
            )
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_object_registered,
                valueRes = getStringBy(accountingObject.registered)
            )
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_object_commissioned,
                valueRes = getStringBy(accountingObject.commissioned)
            )
        )

        categorySyncEntity?.name?.let {
            add(ObjectInfoDomain(R.string.accounting_object_category, it))
        }

        add(
            ObjectInfoDomain(
                R.string.accounting_object_traceable,
                valueRes = getStringBy(accountingObject.traceable)
            )
        )

        accountingObject.commissioningDate?.let {
            add(
                ObjectInfoDomain(
                    R.string.accounting_object_category,
                    getTextDateFromStringDate(it)
                )
            )
        }

        producer?.let {
            add(ObjectInfoDomain(R.string.accounting_objects_producer, it.name.orEmpty()))
        }

        provider?.let {
            add(ObjectInfoDomain(R.string.accounting_objects_provider, it.name.orEmpty()))
        }

        accountingObject.barcodeValue?.let {
            add(ObjectInfoDomain(R.string.common_barcode, it))
        }

        accountingObject.rfidValue?.let {
            add(ObjectInfoDomain(R.string.accounting_object_rfid, it))
        }

        accountingObject.nfc?.let {
            add(ObjectInfoDomain(R.string.common_nfc, it))
        }
    }


    return AccountingObjectDomain(
        id = accountingObject.id,
        title = accountingObject.name,
        status = accountingObject.status?.toDomainStatus(),
        isBarcode = accountingObject.barcodeValue != null,
        listMainInfo = listMainInfo,
        listAdditionallyInfo = emptyList(),
        barcodeValue = accountingObject.barcodeValue,
        rfidValue = accountingObject.rfidValue,
    )
}

fun getStringBy(isTrue: Boolean) =
    if (isTrue) {
        R.string.common_yes
    } else {
        R.string.common_no
    }
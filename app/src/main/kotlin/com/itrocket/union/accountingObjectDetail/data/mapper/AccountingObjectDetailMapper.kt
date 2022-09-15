package com.itrocket.union.accountingObjectDetail.data.mapper

import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectScanningData
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.data.mapper.toDomainStatus
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.utils.getStringDateFromMillis
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

        structuralSyncEntities?.let {
            add(ObjectInfoDomain(R.string.manual_structural, it.joinToString(", ") { it.name }))
        }

        balanceUnitSyncEntities?.let {
            add(ObjectInfoDomain(R.string.balance_unit, it.joinToString(", ") { it.name }))
        }

        location?.let {
            add(
                ObjectInfoDomain(
                    R.string.accounting_objects_location,
                    it.joinToString(", ") { it.name })
            )
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

        add(
            ObjectInfoDomain(
                R.string.accounting_object_marked,
                value = accountingObject.marked.toString()
            )
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_object_for_write_off,
                value = accountingObject.forWriteOff.toString()
            )
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_object_written_off,
                value = accountingObject.writtenOff.toString()
            )
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_object_registered,
                value = accountingObject.registered.toString()
            )
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_object_commissioned,
                value = accountingObject.commissioned.toString()
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

        accountingObject.dateInsert?.let {
            add(ObjectInfoDomain(R.string.common_date_create, getStringDateFromMillis(it)))
        }
        accountingObject.userInserted?.let {
            add(ObjectInfoDomain(R.string.common_user_create, it))
        }
        accountingObject.updateDate?.let {
            add(ObjectInfoDomain(R.string.common_date_update, getStringDateFromMillis(it)))
        }
        accountingObject.userUpdated?.let {
            add(ObjectInfoDomain(R.string.common_user_update, it))
        }

        simpleAdditionalFields?.forEach {
            add(ObjectInfoDomain(name = it.name, value = it.value))
        }

        vocabularyAdditionalFields?.forEach {
            add(ObjectInfoDomain(name = it.name, value = it.value))
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
        factoryNumber = accountingObject.factoryNumber
    )
}

fun AccountingObjectDomain.toAccountingObjectScanningData() = AccountingObjectScanningData(
    id = id,
    factoryNumber = factoryNumber,
    barcodeValue = barcodeValue,
    rfidValue = rfidValue
)

fun getStringBy(isTrue: Boolean) =
    if (isTrue) {
        R.string.common_yes
    } else {
        R.string.common_no
    }
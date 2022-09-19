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

        add(ObjectInfoDomain(R.string.common_sub_name, accountingObject.subName))

        add(ObjectInfoDomain(R.string.common_code, accountingObject.code))

        add(
            ObjectInfoDomain(
                R.string.accounting_objects_factory_num,
                accountingObject.factoryNumber
            )
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_objects_inventory_num,
                accountingObject.inventoryNumber
            )
        )

        add(
            ObjectInfoDomain(
                R.string.manual_structural,
                structuralSyncEntities?.joinToString(", ") { it.name })
        )


        add(
            ObjectInfoDomain(
                R.string.balance_unit,
                balanceUnitSyncEntities?.joinToString(", ") { it.name })
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_objects_location,
                location?.joinToString(", ") { it.name })
        )

        add(
            ObjectInfoDomain(
                R.string.accounting_objects_current_status,
                accountingObject.status?.name
            )
        )

        add(ObjectInfoDomain(R.string.accounting_object_mol, mol?.fullName))

        add(ObjectInfoDomain(R.string.manual_exploiting, exploitingEmployee?.fullName))


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


        add(ObjectInfoDomain(R.string.accounting_object_category, categorySyncEntity?.name))


        add(
            ObjectInfoDomain(
                R.string.accounting_object_traceable,
                valueRes = getStringBy(accountingObject.traceable)
            )
        )

        add(ObjectInfoDomain(R.string.accounting_objects_producer, producer?.name.orEmpty()))

        add(ObjectInfoDomain(R.string.accounting_objects_provider, provider?.name.orEmpty()))

        add(ObjectInfoDomain(R.string.common_barcode, accountingObject.barcodeValue))

        add(ObjectInfoDomain(R.string.accounting_object_rfid, accountingObject.rfidValue))

        add(ObjectInfoDomain(R.string.common_nfc, accountingObject.nfc))

        add(
            ObjectInfoDomain(
                R.string.common_date_create,
                getStringDateFromMillis(accountingObject.dateInsert)
            )
        )
        add(ObjectInfoDomain(R.string.common_user_create, accountingObject.userInserted))
        add(
            ObjectInfoDomain(
                R.string.common_date_update,
                getStringDateFromMillis(accountingObject.updateDate)
            )
        )
        add(ObjectInfoDomain(R.string.common_user_update, accountingObject.userUpdated))
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
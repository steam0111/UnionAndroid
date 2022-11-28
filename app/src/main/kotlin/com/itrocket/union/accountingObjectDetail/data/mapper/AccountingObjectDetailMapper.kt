package com.itrocket.union.accountingObjectDetail.data.mapper

import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectScanningData
import com.example.union_sync_api.entity.AccountingObjectWriteOff
import com.example.union_sync_api.entity.EnumSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.data.mapper.toDomainStatus
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoType
import com.itrocket.union.utils.getStringDateFromMillis


fun AccountingObjectDetailSyncEntity.toAccountingObjectDetailDomain(): AccountingObjectDomain {
    val listMainInfo = buildList {
        add(
            ObjectInfoDomain(
                title = R.string.common_name,
                value = accountingObject.name,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.common_sub_name,
                value = accountingObject.subName,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.common_code,
                value = accountingObject.code,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_objects_factory_num,
                value = accountingObject.factoryNumber,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_objects_inventory_num,
                value = accountingObject.inventoryNumber,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.manual_structural,
                value = structuralSyncEntities?.joinToString(", ") { it.name },
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.balance_unit,
                value = balanceUnitSyncEntities?.joinToString(", ") { it.name },
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_objects_location,
                value = location?.joinToString(", ") { it.name },
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_objects_current_status,
                value = accountingObject.status?.name,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_object_mol,
                value = mol?.fullName,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.manual_exploiting,
                value = exploitingEmployee?.fullName,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_object_marked,
                valueRes = getStringBy(accountingObject.marked) ?: R.string.value_not_defined,
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_object_for_write_off,
                valueRes = getStringBy(accountingObject.forWriteOff) ?: R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_object_written_off,
                valueRes = getStringBy(accountingObject.writtenOff) ?: R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_object_registered,
                valueRes = getStringBy(accountingObject.registered) ?: R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_object_commissioned,
                valueRes = getStringBy(accountingObject.commissioned) ?: R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_object_category,
                value = categorySyncEntity?.name,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_object_traceable,
                valueRes = getStringBy(accountingObject.traceable) ?: R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_objects_producer,
                value = producer?.name,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_objects_provider,
                value = provider?.name,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.common_barcode,
                value = accountingObject.barcodeValue,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.accounting_object_rfid,
                value = accountingObject.rfidValue,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.common_nfc,
                value = accountingObject.nfc,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.common_date_create,
                value = accountingObject.dateInsert?.let { getStringDateFromMillis(it) },
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.common_user_create,
                value = accountingObject.userInserted,
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.common_date_update,
                value = accountingObject.updateDate?.let { getStringDateFromMillis(it) },
                valueRes = R.string.value_not_defined
            )
        )
        add(
            ObjectInfoDomain(
                title = R.string.common_user_update,
                value = accountingObject.userUpdated,
                valueRes = R.string.value_not_defined
            )
        )
    }

    val additionalFields = buildList {
        simpleAdditionalFields?.forEach {
            add(
                ObjectInfoDomain(
                    name = it.name,
                    value = it.value,
                    valueRes = R.string.value_not_defined,
                    filedType = ObjectInfoType.SIMPLE_ADDITIONAL_FIELD
                )
            )
        }

        vocabularyAdditionalFields?.forEach {
            add(
                ObjectInfoDomain(
                    name = it.name,
                    value = it.value,
                    valueRes = R.string.value_not_defined,
                    filedType = ObjectInfoType.VOCABULARY_ADDITIONAL_FIELD
                )
            )
        }
    }

    val characteristics = buildList {
        simpleCharacteristic?.forEach {
            add(
                ObjectInfoDomain(
                    name = it.name,
                    value = it.value,
                    valueRes = R.string.value_not_defined,
                    filedType = ObjectInfoType.SIMPLE_CHARACTERISTICS
                )
            )
        }

        vocabularyCharacteristic?.forEach {
            add(
                ObjectInfoDomain(
                    name = it.name,
                    value = it.value,
                    valueRes = R.string.value_not_defined,
                    filedType = ObjectInfoType.VOCABULARY_CHARACTERISTICS
                )
            )
        }
    }


    return AccountingObjectDomain(
        id = accountingObject.id,
        title = accountingObject.name,
        status = accountingObject.status?.toDomainStatus(),
        isBarcode = accountingObject.barcodeValue != null,
        listMainInfo = listMainInfo,
        listAdditionallyInfo = additionalFields,
        characteristics = characteristics,
        barcodeValue = accountingObject.barcodeValue,
        rfidValue = accountingObject.rfidValue,
        factoryNumber = accountingObject.factoryNumber,
        marked = accountingObject.marked,
        forWrittenOff = accountingObject.forWriteOff
    )
}

fun AccountingObjectDomain.toAccountingObjectScanningData() = AccountingObjectScanningData(
    id = id,
    factoryNumber = factoryNumber,
    barcodeValue = barcodeValue,
    rfidValue = rfidValue
)

fun AccountingObjectDomain.toAccountingObjectWriteOff() =
    AccountingObjectWriteOff(id = id, forWriteOff = true)

fun getStringBy(isTrue: Boolean?) =
    when (isTrue) {
        true -> R.string.common_yes
        false -> R.string.common_no
        else -> null
    }
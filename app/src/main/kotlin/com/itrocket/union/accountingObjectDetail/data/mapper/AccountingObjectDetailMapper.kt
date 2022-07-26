package com.itrocket.union.accountingObjectDetail.data.mapper

import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.data.mapper.toDomainStatus
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain


fun AccountingObjectDetailSyncEntity.toAccountingObjectDetailDomain(): AccountingObjectDomain {
    val listMainInfo = mutableListOf<ObjectInfoDomain>()
    accountingObject.status?.name?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_current_status, it))
    }
    accountingObject.factoryNumber?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_factory_num, it))
    }
    accountingObject.inventoryNumber?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_inventory_num, it))
    }
    location?.name?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_location, it))
    }
    mol?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.manual_mol, it.fullName))
    }
    exploitingEmployee?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.manual_exploiting, it.fullName))
    }
    organization?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.manual_organization, it.name))
    }
    department?.let {
        listMainInfo.add(
            ObjectInfoDomain(
                R.string.accounting_objects_department,
                it.name.orEmpty()
            )
        )
    }
    accountingObject.model?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_model, it))
    }
    accountingObject.actualPrice?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_price, it))
    }
    producer?.let {
        listMainInfo.add(
            ObjectInfoDomain(
                R.string.accounting_objects_producer,
                it.name.orEmpty()
            )
        )
    }
    equipmentType?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_type, it.name))
    }
    provider?.let {
        listMainInfo.add(
            ObjectInfoDomain(
                R.string.accounting_objects_provider,
                it.name.orEmpty()
            )
        )
    }
    accountingObject.count?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_count, it.toString()))
    }
    accountingObject.rfidValue?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.common_rfid, it))
    }
    accountingObject.barcodeValue?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.reading_mode_barcode, it))
    }
    branch?.let {
        listMainInfo.add(ObjectInfoDomain(R.string.balance_unit, it.name))
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
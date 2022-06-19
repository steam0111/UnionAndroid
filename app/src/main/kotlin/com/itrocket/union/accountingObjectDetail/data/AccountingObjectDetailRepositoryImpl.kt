package com.itrocket.union.accountingObjectDetail.data

import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.entity.FullAccountingObject
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.union.accountingObjects.data.mapper.toDomainStatus
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain

class AccountingObjectDetailRepositoryImpl(
    private val dao: AccountingObjectDao
) : AccountingObjectDetailRepository {

    override suspend fun getAccountingObject(id: String): AccountingObjectDomain {
        return dao.getById(id).toDomain()
    }

    fun FullAccountingObject.toDomain(): AccountingObjectDomain {
        val listMainInfo = mutableListOf<ObjectInfoDomain>()
        accountingObjectDb.status?.name?.let {
            listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_current_status, it))
        }
        accountingObjectDb.factoryNumber?.let {
            listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_factory_num, it))
        }
        accountingObjectDb.inventoryNumber?.let {
            listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_inventory_num, it))
        }
        locationDb?.name?.let {
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
        accountingObjectDb.model?.let {
            listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_model, it))
        }
        accountingObjectDb.actualPrice?.let {
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
        accountingObjectDb.count?.let {
            listMainInfo.add(ObjectInfoDomain(R.string.accounting_objects_count, it.toString()))
        }
        accountingObjectDb.rfidValue?.let {
            listMainInfo.add(ObjectInfoDomain(R.string.common_rfid, it))
        }
        accountingObjectDb.barcodeValue?.let {
            listMainInfo.add(ObjectInfoDomain(R.string.reading_mode_barcode, it))
        }

        return AccountingObjectDomain(
            id = accountingObjectDb.id,
            title = accountingObjectDb.name,
            status = accountingObjectDb.status?.toDomainStatus(),
            isBarcode = accountingObjectDb.barcodeValue != null,
            listMainInfo = listMainInfo,
            listAdditionallyInfo = emptyList()
        )
    }
}
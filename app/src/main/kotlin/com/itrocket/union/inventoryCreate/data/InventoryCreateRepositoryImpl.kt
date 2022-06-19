package com.itrocket.union.inventoryCreate.data

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.inventoryCreate.domain.dependencies.InventoryCreateRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus

class InventoryCreateRepositoryImpl(private val coreDispatchers: CoreDispatchers) :
    InventoryCreateRepository {
    override suspend fun getAccountingObjectsByIds(listAccountingObjectIds: List<String>): List<AccountingObjectDomain> {
        return listOf(
            AccountingObjectDomain(
                id = "8",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung ЕК288, 23 дюйма",
                status = ObjectStatus("AVAILABLE", ObjectStatusType.AVAILABLE),
                inventoryStatus = InventoryAccountingObjectStatus.NEW,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        R.string.accounting_objects_factory_num,
                        "AV169V100E00442"
                    ),
                    ObjectInfoDomain(
                        R.string.accounting_objects_inventory_num,
                        "6134509345098749"
                    ),
                    ObjectInfoDomain(
                        R.string.manual_location,
                        "Склад хранения"
                    ),
                    ObjectInfoDomain(
                        R.string.manual_exploiting,
                        "Иванов Иван Иванович"
                    ),
                    ObjectInfoDomain(
                        R.string.manual_organization,
                        "Иванов Иван Иванович"
                    ),
                ),
                listAdditionallyInfo = listOf()
            )
        )
    }

}
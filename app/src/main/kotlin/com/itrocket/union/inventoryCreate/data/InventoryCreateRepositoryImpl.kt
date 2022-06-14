package com.itrocket.union.inventoryCreate.data

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.inventoryCreate.domain.dependencies.InventoryCreateRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryStatus

class InventoryCreateRepositoryImpl(private val coreDispatchers: CoreDispatchers) :
    InventoryCreateRepository {
    override suspend fun getAccountingObjectsByIds(listAccountingObjectIds: List<String>): List<AccountingObjectDomain> {
        return listOf(
            AccountingObjectDomain(
                id = "8",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung ЕК288, 23 дюйма",
                status = ObjectStatus.AVAILABLE,
                inventoryStatus = InventoryStatus.NEW,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской №",
                        "AV169V100E00442"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный №",
                        "6134509345098749"
                    ),
                    ObjectInfoDomain(
                        "Местоположение",
                        "Склад хранения"
                    ),
                    ObjectInfoDomain(
                        "МОЛ",
                        "Иванов Иван Иванович"
                    ),
                    ObjectInfoDomain(
                        "Эксплуатирующий",
                        "Иванов Иван Иванович"
                    ),
                    ObjectInfoDomain(
                        "Организация",
                        "ОАО «Вымпелком»"
                    ),
                ),
                listAdditionallyInfo = listOf()
            )
        )
    }

}
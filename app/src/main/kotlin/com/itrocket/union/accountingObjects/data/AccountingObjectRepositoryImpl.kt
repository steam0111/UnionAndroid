package com.itrocket.union.accountingObjects.data

import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus

class AccountingObjectRepositoryImpl : AccountingObjectRepository {

    override suspend fun getAccountingObjects() =
        listOf(
            AccountingObjectDomain(
                id = "1",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung",
                status = ObjectStatus.AVAILABLE,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                )
            ),
            AccountingObjectDomain(
                id = "2",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung",
                status = ObjectStatus.DECOMMISSIONED,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf()
            ), AccountingObjectDomain(
                id = "3",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung",
                status = ObjectStatus.UNDER_REPAIR,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf()
            ),
            AccountingObjectDomain(
                id = "4",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung",
                status = ObjectStatus.UNDER_REVIEW,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf()
            ),
            AccountingObjectDomain(
                id = "5",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung",
                status = ObjectStatus.UNDER_REPAIR,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf()
            ),
            AccountingObjectDomain(
                id = "6",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung",
                status = ObjectStatus.DECOMMISSIONED,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "123123123123123333"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf()
            )
        )

}
package com.itrocket.union.inventories.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.inventories.domain.dependencies.InventoriesRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain

class InventoriesInteractor(
    private val repository: InventoriesRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getInventories() = withContext(coreDispatchers.io) {
        listOf(
            InventoryCreateDomain(
                number = "БП-00001374",
                time = "12:40",
                date = "12.12.12",
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                ),
                accountingObjectList = listOf(
                    AccountingObjectDomain(
                        id = "7",
                        isBarcode = true,
                        title = "Ширикоформатный жидкокристалический монитор Samsung2",
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
                        ),
                        listAdditionallyInfo = listOf()
                    ),
                    AccountingObjectDomain(
                        id = "8",
                        isBarcode = true,
                        title = "Ширикоформатный жидкокристалический монитор Samsung2",
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
                        ),
                        listAdditionallyInfo = listOf()
                    )
                )
            ),
            InventoryCreateDomain(
                number = "БП-00001375",
                time = "12:40",
                date = "12.12.12",
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                ),
                accountingObjectList = listOf()
            ),
            InventoryCreateDomain(
                number = "БП-00001376",
                time = "12:40",
                date = "12.12.12",
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                ),
                accountingObjectList = listOf()
            ),
            InventoryCreateDomain(
                number = "БП-00001377",
                time = "12:40",
                date = "12.12.12",
                documentInfo = listOf(
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                    "Систмный интегратор",
                ),
                accountingObjectList = listOf()
            ),
        )
    }
}
package com.itrocket.union.identify.data

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.identify.domain.dependencies.IdentifyRepository
import com.itrocket.union.identify.domain.entity.IdentifyDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus

class IdentifyRepositoryImpl: IdentifyRepository {
    override suspend fun getIdentify(): List<AccountingObjectDomain> = listOf(
        AccountingObjectDomain(
            id = "4",
            title = "Name",
            status = null,
            inventoryStatus = InventoryAccountingObjectStatus.NOT_FOUND,
            listAdditionallyInfo = listOf(),
            listMainInfo = listOf(),
            rfidValue = null,
            isBarcode = true,
            barcodeValue = null
        ),
        AccountingObjectDomain(
            id = "5",
            title = "Name2",
            status = null,
            inventoryStatus = InventoryAccountingObjectStatus.NOT_FOUND,
            listAdditionallyInfo = listOf(),
            listMainInfo = listOf(),
            rfidValue = null,
            isBarcode = true,
            barcodeValue = null
        ),
    )
}
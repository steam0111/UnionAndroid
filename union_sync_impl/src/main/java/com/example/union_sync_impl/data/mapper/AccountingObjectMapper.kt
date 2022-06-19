package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectStatus
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.FullAccountingObject
import org.openapitools.client.models.AccountingObjectStatusDto
import org.openapitools.client.models.CustomAccountingObjectDto

fun CustomAccountingObjectDto.toAccountingObjectDb(): AccountingObjectDb {
    return AccountingObjectDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        organizationId = organizationId,
        locationId = locationId,
        molId = molId,
        exploitingId = exploitingId,
        nomenclatureId = nomenclatureId,
        nomenclatureGroupId = nomenclatureGroupId,
        barcodeValue = barcodeValue,
        name = name.orEmpty(),
        rfidValue = rfidValue,
        factoryNumber = factoryNumber,
        inventoryNumber = inventoryNumber,
        status = extendedAccountingObjectStatus?.toStatus()
    )
}

fun FullAccountingObject.toSyncEntity(
    locationSyncEntity: LocationSyncEntity?
): AccountingObjectSyncEntity {
    return AccountingObjectSyncEntity(
        id = accountingObjectDb.id,
        catalogItemName = accountingObjectDb.catalogItemName,
        organizationId = accountingObjectDb.organizationId,
        locationId = accountingObjectDb.locationId,
        molId = accountingObjectDb.molId,
        exploitingId = accountingObjectDb.exploitingId,
        nomenclatureId = accountingObjectDb.nomenclatureId,
        nomenclatureGroupId = accountingObjectDb.nomenclatureGroupId,
        barcodeValue = accountingObjectDb.barcodeValue,
        name = accountingObjectDb.name,
        rfidValue = accountingObjectDb.rfidValue,
        factoryNumber = accountingObjectDb.factoryNumber,
        inventoryNumber = accountingObjectDb.inventoryNumber,
        locationSyncEntity = locationSyncEntity,
        status = accountingObjectDb.status
    )
}

fun AccountingObjectStatusDto.toStatus() = AccountingObjectStatus(id.orEmpty(), name.orEmpty())
package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_impl.entity.AccountingObjectDb
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
        rfidValue = rfidValue
    )
}

fun AccountingObjectDb.toSyncEntity(): AccountingObjectSyncEntity {
    return AccountingObjectSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        organizationId = organizationId,
        locationId = locationId,
        molId = molId,
        exploitingId = exploitingId,
        nomenclatureId = nomenclatureId,
        nomenclatureGroupId = nomenclatureGroupId,
        barcodeValue = barcodeValue,
        name = name,
        rfidValue = rfidValue
    )
}
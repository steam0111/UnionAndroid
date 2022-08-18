package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectScanningData
import com.example.union_sync_api.entity.AccountingObjectStatusSyncEntity
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.AccountingObjectUpdateSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.AccountingObjectScanningUpdate
import com.example.union_sync_impl.entity.AccountingObjectStatusDb
import com.example.union_sync_impl.entity.AccountingObjectUpdate
import com.example.union_sync_impl.entity.FullAccountingObject
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getServerFormatFromMillis
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.AccountingObjectDtoV2
import org.openapitools.client.models.AccountingObjectStatusDto
import org.openapitools.client.models.EnumDtoV2

fun AccountingObjectDtoV2.toAccountingObjectDb(): AccountingObjectDb {
    return AccountingObjectDb(
        id = id,
        catalogItemName = catalogItemName.orEmpty(),
        structuralId = structuralUnitId,
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
        status = extendedAccountingObjectStatus?.toStatusDb(),
        statusId = accountingObjectStatusId,
        producerId = producerId,
        equipmentTypeId = typeId,
        actualPrice = actualPrice,
        count = count?.toInt(),
        commissioningDate = commissioningDate,
        internalNumber = internalNumber,
        model = model,
        providerId = providerId,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        insertDate = getMillisDateFromServerFormat(dateInsert),
        userUpdated = userUpdated,
        userInserted = userInserted,
        subName = subName,
        code = code,
        marked = marked ?: false,
        forWriteOff = forWriteOff ?: false,
        writtenOff = writtenOff ?: false,
        registered = registered ?: false,
        commissioned = commissioned ?: false,
        accountingObjectCategoryId = accountingObjectCategoryId,
        invoiceNumber = invoiceNumber,
        nfc = nfcValue,
        traceable = traceable ?: false
    )
}

fun FullAccountingObject.toAccountingObjectDetailSyncEntity(
    locationSyncEntity: LocationSyncEntity?,
    balanceUnitSyncEntity: StructuralSyncEntity?
): AccountingObjectDetailSyncEntity {
    return AccountingObjectDetailSyncEntity(
        accountingObject = accountingObjectDb.toSyncEntity(locationSyncEntity),
        location = locationSyncEntity,
        exploitingEmployee = exploitingEmployee?.toSyncEntity(),
        producer = producer?.toSyncEntity(),
        equipmentType = equipmentType?.toSyncEntity(),
        provider = provider?.toSyncEntity(),
        mol = mol?.toSyncEntity(),
        structuralSyncEntity = structuralDb?.toStructuralSyncEntity(),
        categorySyncEntity = categoryDb?.toSyncEntity(),
        balanceUnitSyncEntity = balanceUnitSyncEntity
    )
}

fun AccountingObjectStatusDto.toStatusDb() = AccountingObjectStatusDb(id.orEmpty(), name)

fun EnumDtoV2.toStatusDb() = AccountingObjectStatusDb(id.orEmpty(), name)

fun AccountingObjectStatusSyncEntity.toStatusDb() = AccountingObjectStatusDb(id, name)

fun AccountingObjectDb.toSyncEntity(locationSyncEntity: LocationSyncEntity?) =
    AccountingObjectSyncEntity(
        id = id,
        catalogItemName = catalogItemName,
        barcodeValue = barcodeValue,
        name = name,
        rfidValue = rfidValue,
        factoryNumber = factoryNumber,
        inventoryNumber = inventoryNumber,
        actualPrice = actualPrice,
        count = count,
        commissioningDate = commissioningDate,
        status = status?.toSyncEntity(),
        statusId = statusId,
        providerId = providerId,
        producerId = producerId,
        equipmentTypeId = equipmentTypeId,
        locationId = locationId,
        molId = molId,
        exploitingEmployeeId = exploitingId,
        internalNumber = internalNumber,
        model = model,
        locationSyncEntity = locationSyncEntity,
        nomenclatureId = nomenclatureId,
        nomenclatureGroupId = nomenclatureGroupId,
        structuralId = structuralId,
        subName = subName,
        code = code,
        marked = marked,
        forWriteOff = forWriteOff,
        writtenOff = writtenOff,
        commissioned = commissioned,
        registered = registered,
        invoiceNumber = invoiceNumber,
        nfc = nfc,
        traceable = traceable,
        updateDate = updateDate,
        dateInsert = insertDate,
        userInserted = userInserted,
        userUpdated = userUpdated
    )

fun FullAccountingObject.toSyncEntity(
    inventoryStatus: String? = null,
    locationSyncEntity: LocationSyncEntity?
) = AccountingObjectSyncEntity(
    id = accountingObjectDb.id,
    catalogItemName = accountingObjectDb.catalogItemName,
    barcodeValue = accountingObjectDb.barcodeValue,
    name = accountingObjectDb.name,
    rfidValue = accountingObjectDb.rfidValue,
    factoryNumber = accountingObjectDb.factoryNumber,
    inventoryStatus = inventoryStatus,
    inventoryNumber = accountingObjectDb.inventoryNumber,
    actualPrice = accountingObjectDb.actualPrice,
    count = accountingObjectDb.count,
    commissioningDate = accountingObjectDb.commissioningDate,
    status = accountingObjectDb.status?.toSyncEntity(),
    providerId = accountingObjectDb.providerId,
    producerId = accountingObjectDb.producerId,
    equipmentTypeId = accountingObjectDb.equipmentTypeId,
    locationId = accountingObjectDb.locationId,
    molId = accountingObjectDb.molId,
    exploitingEmployeeId = accountingObjectDb.exploitingId,
    internalNumber = accountingObjectDb.internalNumber,
    model = accountingObjectDb.model,
    locationSyncEntity = locationSyncEntity,
    statusId = accountingObjectDb.statusId,
    nomenclatureGroupId = accountingObjectDb.nomenclatureGroupId,
    nomenclatureId = accountingObjectDb.nomenclatureId,
    structuralId = structuralDb?.id,
    subName = accountingObjectDb.subName,
    code = accountingObjectDb.code,
    writtenOff = accountingObjectDb.writtenOff,
    forWriteOff = accountingObjectDb.forWriteOff,
    marked = accountingObjectDb.marked,
    commissioned = accountingObjectDb.commissioned,
    registered = accountingObjectDb.registered,
    invoiceNumber = accountingObjectDb.invoiceNumber,
    nfc = accountingObjectDb.nfc,
    traceable = accountingObjectDb.traceable,
    updateDate = accountingObjectDb.updateDate,
    dateInsert = accountingObjectDb.insertDate,
    userUpdated = accountingObjectDb.userUpdated,
    userInserted = accountingObjectDb.userInserted
)

fun List<FullAccountingObject>.toAccountingObjectDtosV2(): List<AccountingObjectDtoV2> {
    return map { fullAccountingObject ->
        val accountingObjectDb = fullAccountingObject.accountingObjectDb
        AccountingObjectDtoV2(
            // todo
            id = accountingObjectDb.id,
            locationId = accountingObjectDb.locationId,
            exploitingId = accountingObjectDb.exploitingId,
            accountingObjectStatusId = accountingObjectDb.status?.id,
            dateUpdate = getStringDateFromMillis(accountingObjectDb.updateDate),
            catalogItemName = accountingObjectDb.catalogItemName,
            barcodeValue = accountingObjectDb.barcodeValue,
            actualPrice = accountingObjectDb.actualPrice,
            startingPrice = accountingObjectDb.actualPrice,
            name = accountingObjectDb.name,
            model = accountingObjectDb.model,
            structuralUnitId = accountingObjectDb.structuralId,
            molId = accountingObjectDb.molId,
            producerId = accountingObjectDb.producerId,
            typeId = accountingObjectDb.equipmentTypeId,
            providerId = accountingObjectDb.providerId,
            factoryNumber = accountingObjectDb.factoryNumber,
            internalNumber = accountingObjectDb.internalNumber,
            inventoryNumber = accountingObjectDb.inventoryNumber,
            rfidValue = accountingObjectDb.rfidValue,
            nfcValue = accountingObjectDb.nfc,
            count = accountingObjectDb.count?.toLong(),
            nomenclatureId = accountingObjectDb.nomenclatureId,
            nomenclatureGroupId = accountingObjectDb.nomenclatureGroupId,
            userInserted = accountingObjectDb.userInserted,
            userUpdated = accountingObjectDb.userUpdated,
            deleted = false,
        )
    }
}

fun AccountingObjectStatusDb.toSyncEntity() = AccountingObjectStatusSyncEntity(id, name)

fun AccountingObjectUpdateSyncEntity.toAccountingObjectUpdate(): AccountingObjectUpdate {
    return AccountingObjectUpdate(
        id = id,
        locationId = locationId,
        exploitingId = exploitingId,
        status = status?.toStatusDb(),
        statusId = statusId,
        molId = molId,
        structuralId = structuralId,
        userUpdated = userUpdated,
        updateDate = System.currentTimeMillis()
    )
}

fun AccountingObjectScanningData.toAccountingObjectScanningUpdate() =
    AccountingObjectScanningUpdate(
        id = id,
        barcodeValue = barcodeValue,
        rfidValue = rfidValue,
        factoryNumber = factoryNumber
    )

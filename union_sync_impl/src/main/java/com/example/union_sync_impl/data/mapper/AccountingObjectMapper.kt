package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectAdditionalFieldSyncEntity
import com.example.union_sync_api.entity.AccountingObjectCharacteristicSyncEntity
import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.LabelType
import com.example.union_sync_api.entity.AccountingObjectScanningData
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.AccountingObjectUpdateSyncEntity
import com.example.union_sync_api.entity.AccountingObjectWriteOff
import com.example.union_sync_api.entity.EnumSyncEntity
import com.example.union_sync_api.entity.EnumType
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.AccountingObjectLabelTypeUpdate
import com.example.union_sync_impl.entity.AccountingObjectScanningUpdate
import com.example.union_sync_impl.entity.AccountingObjectUpdate
import com.example.union_sync_impl.entity.AccountingObjectWriteOffUpdate
import com.example.union_sync_impl.entity.FullAccountingObject
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.AccountingObjectDtoV2

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
        status = extendedAccountingObjectStatus?.toEnumDb(EnumType.ACCOUNTING_OBJECT_STATUS),
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
        traceable = traceable ?: false,
        cancel = deleted,
        labelTypeId = labelTypeId
    )
}

fun FullAccountingObject.toAccountingObjectDetailSyncEntity(
    locationSyncEntity: List<LocationSyncEntity>?,
    balanceUnitSyncEntities: List<StructuralSyncEntity>?,
    structuralSyncEntities: List<StructuralSyncEntity>?,
    simpleAdditionalFields: List<AccountingObjectAdditionalFieldSyncEntity>?,
    vocabularyAdditionalFields: List<AccountingObjectAdditionalFieldSyncEntity>?,
    simpleCharacteristic: List<AccountingObjectCharacteristicSyncEntity>?,
    vocabularyCharacteristic: List<AccountingObjectCharacteristicSyncEntity>?,
    categorySyncEntity: EnumSyncEntity?
): AccountingObjectDetailSyncEntity {
    return AccountingObjectDetailSyncEntity(
        accountingObject = accountingObjectDb.toSyncEntity(locationSyncEntity),
        location = locationSyncEntity,
        exploitingEmployee = exploitingEmployee?.toSyncEntity(),
        producer = producer?.toSyncEntity(),
        equipmentType = equipmentType?.toSyncEntity(),
        provider = provider?.toSyncEntity(),
        mol = mol?.toSyncEntity(),
        structuralSyncEntities = structuralSyncEntities,
        categorySyncEntity = categorySyncEntity,
        balanceUnitSyncEntities = balanceUnitSyncEntities,
        simpleAdditionalFields = simpleAdditionalFields,
        vocabularyAdditionalFields = vocabularyAdditionalFields,
        simpleCharacteristic = simpleCharacteristic,
        vocabularyCharacteristic = vocabularyCharacteristic,
        labelType = labelTypeDb?.toSyncEntity()
    )
}

fun AccountingObjectDb.toSyncEntity(locationSyncEntity: List<LocationSyncEntity>?) =
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
        userUpdated = userUpdated,
        labelTypeId = labelTypeId
    )

fun FullAccountingObject.toSyncEntity(
    inventoryStatus: String? = null,
    comment: String? = null,
    manualInput: Boolean? = null,
    locationSyncEntity: List<LocationSyncEntity>?
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
    userInserted = accountingObjectDb.userInserted,
    comment = comment,
    manualInput = manualInput,
    labelTypeId = labelTypeDb?.id
)

fun List<AccountingObjectDb>.toAccountingObjectDtosV2(): List<AccountingObjectDtoV2> {
    return map { accountingObjectDb ->
        AccountingObjectDtoV2(
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
            deleted = accountingObjectDb.cancel ?: false,
            dateInsert = getStringDateFromMillis(accountingObjectDb.insertDate),
            forWriteOff = accountingObjectDb.forWriteOff,
            labelTypeId = accountingObjectDb.labelTypeId
        )
    }
}

fun AccountingObjectUpdateSyncEntity.toAccountingObjectUpdate(): AccountingObjectUpdate {
    return AccountingObjectUpdate(
        id = id,
        locationId = locationId,
        exploitingId = exploitingId,
        status = status?.toEnumDb(),
        statusId = statusId,
        molId = molId,
        structuralId = structuralId,
        userUpdated = userUpdated,
        updateDate = System.currentTimeMillis()
    )
}

fun AccountingObjectWriteOff.toAccountingObjectWriteOffUpdate() =
    AccountingObjectWriteOffUpdate(
        id = id,
        forWriteOff = forWriteOff,
    )

fun AccountingObjectScanningData.toAccountingObjectScanningUpdate() =
    AccountingObjectScanningUpdate(
        id = id,
        barcodeValue = barcodeValue,
        rfidValue = rfidValue,
        factoryNumber = factoryNumber
    )

fun LabelType.toAccountingObjectLabelTypeUpdate() =
    AccountingObjectLabelTypeUpdate(id = id, labelTypeId = labelTypeId)
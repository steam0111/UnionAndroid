package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectStatus
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.AccountingObjectStatusDb
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
        status = extendedAccountingObjectStatus?.toStatus(),
        producerId = producerId,
        equipmentTypeId = typeId,
        actualPrice = actualPrice,
        count = count?.toInt(),
        commissioningDate = commissioningDate,
        internalNumber = internalNumber,
        departmentId = departmentId,
        model = model,
        providerId = providerId
    )
}

fun FullAccountingObject.toAccountingObjectDetailSyncEntity(
    locationSyncEntity: LocationSyncEntity?
): AccountingObjectDetailSyncEntity {

    return AccountingObjectDetailSyncEntity(
        accountingObject = accountingObjectDb.toSyncEntity(locationSyncEntity),
        location = locationSyncEntity,
        exploitingEmployee = exploitingEmployee?.toSyncEntity(),
        producer = producer?.toSyncEntity(),
        equipmentType = equipmentType?.toSyncEntity(),
        provider = provider?.toSyncEntity(),
        mol = mol?.toSyncEntity(),
        organization = organization?.toSyncEntity(),
        department = department?.toSyncEntity()
    )
}

fun AccountingObjectStatusDto.toStatus() = AccountingObjectStatusDb(id, name)


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
        status = status?.toStatus(),
        providerId = providerId,
        departmentId = departmentId,
        producerId = producerId,
        equipmentTypeId = equipmentTypeId,
        locationId = locationId,
        molId = molId,
        exploitingEmployeeId = exploitingId,
        organizationId = organizationId,
        internalNumber = internalNumber,
        model = model,
        locationSyncEntity = locationSyncEntity
    )

fun FullAccountingObject.toSyncEntity(
    locationSyncEntity: LocationSyncEntity?,
    inventoryStatus: String? = null
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
    status = accountingObjectDb.status?.toStatus(),
    providerId = accountingObjectDb.providerId,
    departmentId = accountingObjectDb.departmentId,
    producerId = accountingObjectDb.producerId,
    equipmentTypeId = accountingObjectDb.equipmentTypeId,
    locationId = accountingObjectDb.locationId,
    molId = accountingObjectDb.molId,
    exploitingEmployeeId = accountingObjectDb.exploitingId,
    organizationId = accountingObjectDb.organizationId,
    internalNumber = accountingObjectDb.internalNumber,
    model = accountingObjectDb.model,
    locationSyncEntity = locationSyncEntity
)

fun AccountingObjectStatusDb.toStatus() = AccountingObjectStatus(id, name)

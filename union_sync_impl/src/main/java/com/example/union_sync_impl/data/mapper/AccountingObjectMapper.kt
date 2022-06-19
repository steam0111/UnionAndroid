package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectStatus
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.ProducerSyncEntity
import com.example.union_sync_api.entity.EquipmentTypesSyncEntity
import com.example.union_sync_api.entity.ProviderSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_api.entity.DepartmentSyncEntity
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
        status = extendedAccountingObjectStatus?.toStatus(),
        producerId = producerId,
        equipmentTypeId = typeId,
        actualPrice = actualPrice,
        count = count?.toInt(),
        commissioningDate = commissioningDate,
        internalNumber = internalNumber,
        departmentId = departmentId,
        model = model
    )
}

fun FullAccountingObject.toSyncEntity(
    locationSyncEntity: LocationSyncEntity?,
    exploitingEmployee: EmployeeSyncEntity?,
    producerSyncEntity: ProducerSyncEntity?,
    mol: EmployeeSyncEntity?,
    equipmentTypesSyncEntity: EquipmentTypesSyncEntity?,
    providerSyncEntity: ProviderSyncEntity?,
    organizationSyncEntity: OrganizationSyncEntity?,
    departmentSyncEntity: DepartmentSyncEntity?
): AccountingObjectSyncEntity {
    return AccountingObjectSyncEntity(
        id = accountingObjectDb.id,
        catalogItemName = accountingObjectDb.catalogItemName,
        barcodeValue = accountingObjectDb.barcodeValue,
        name = accountingObjectDb.name,
        rfidValue = accountingObjectDb.rfidValue,
        factoryNumber = accountingObjectDb.factoryNumber,
        inventoryNumber = accountingObjectDb.inventoryNumber,
        locationSyncEntity = locationSyncEntity,
        status = accountingObjectDb.status,
        exploitingEmployee = exploitingEmployee,
        actualPrice = accountingObjectDb.actualPrice,
        producerSyncEntity = producerSyncEntity,
        equipmentTypesSyncEntity = equipmentTypesSyncEntity,
        providerSyncEntity = providerSyncEntity,
        commissioningDate = accountingObjectDb.commissioningDate,
        count = accountingObjectDb.count,
        mol = mol,
        organizationSyncEntity = organizationSyncEntity,
        internalNumber = accountingObjectDb.internalNumber,
        departmentSyncEntity = departmentSyncEntity
    )
}

fun AccountingObjectStatusDto.toStatus() = AccountingObjectStatus(id.orEmpty(), name.orEmpty())
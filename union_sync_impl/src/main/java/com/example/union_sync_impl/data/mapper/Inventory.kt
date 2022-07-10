package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.entity.InventoryDb
import org.openapitools.client.models.InventoryDtoV2

fun InventoryDtoV2.toInventoryDb(): InventoryDb {
    return InventoryDb(
        locationIds = listOf(locationId.orEmpty()),
        organizationId = organizationId,
        employeeId = molId,
        date = System.currentTimeMillis(),//creationDate
        updateDate = System.currentTimeMillis(),
        inventoryStatus = extendedInventoryState?.id.orEmpty()
    )
}

fun InventoryCreateSyncEntity.toInventoryDb(id: String): InventoryDb {
    return InventoryDb(
        id = id,
        organizationId = organizationId,
        employeeId = employeeId,
        date = System.currentTimeMillis(),
        locationIds = locationIds,
        inventoryStatus = inventoryStatus,
        updateDate = updateDate
    )
}

fun InventoryUpdateSyncEntity.toInventoryDb(): InventoryDb {
    return InventoryDb(
        id = id,
        organizationId = organizationId,
        employeeId = employeeId,
        date = date,
        locationIds = locationIds,
        inventoryStatus = inventoryStatus,
        updateDate = updateDate
    )
}

fun InventoryDb.toInventorySyncEntity(
    organizationSyncEntity: OrganizationSyncEntity?,
    mol: EmployeeSyncEntity?,
    locationSyncEntities: List<LocationShortSyncEntity>?,
    accountingObjects: List<AccountingObjectSyncEntity>
): InventorySyncEntity {
    return InventorySyncEntity(
        id = id,
        date = date,
        organizationSyncEntity = organizationSyncEntity,
        mol = mol,
        locationSyncEntities = locationSyncEntities,
        accountingObjects = accountingObjects,
        inventoryStatus = inventoryStatus,
        updateDate = updateDate
    )
}
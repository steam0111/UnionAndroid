package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.entity.InventoryDb

fun InventoryCreateSyncEntity.toInventoryDb(): InventoryDb {
    return InventoryDb(
        organizationId = organizationId,
        employeeId = employeeId,
        accountingObjectsIds = accountingObjectsIds,
        date = System.currentTimeMillis(),
        locationIds = locationIds
    )
}

fun InventoryUpdateSyncEntity.toInventoryDb(): InventoryDb {
    return InventoryDb(
        id = id,
        organizationId = organizationId,
        employeeId = employeeId,
        accountingObjectsIds = accountingObjectsIds,
        date = date,
        locationIds = locationIds
    )
}

fun InventoryDb.toInventorySyncEntity(
    organizationSyncEntity: OrganizationSyncEntity,
    mol: EmployeeSyncEntity?,
    locationSyncEntities: List<LocationShortSyncEntity>?,
    accountingObjects: List<AccountingObjectSyncEntity>
): InventorySyncEntity {
    return InventorySyncEntity(
        id = id.toString(),
        date = date,
        organizationSyncEntity = organizationSyncEntity,
        mol = mol,
        locationSyncEntities = locationSyncEntities,
        accountingObjects = accountingObjects
    )
}
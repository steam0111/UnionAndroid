package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.entity.InventoryDb

fun InventoryCreateSyncEntity.toInventoryDb(): InventoryDb {
    return InventoryDb(
        organizationId = organizationId,
        employeeId = employeeId,
        accountingObjectsIds = accountingObjectsIds,
        date = System.currentTimeMillis()
    )
}

fun InventoryDb.toInventorySyncEntity(
    organizationSyncEntity: OrganizationSyncEntity,
    mol: EmployeeSyncEntity
): InventorySyncEntity {
    return InventorySyncEntity(
        id = id.toString(),
        date = date,
        organizationSyncEntity = organizationSyncEntity,
        mol = mol
    )
}
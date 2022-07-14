package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.example.union_sync_api.entity.InventorySyncEntity
import com.example.union_sync_api.entity.InventoryUpdateSyncEntity
import com.example.union_sync_api.entity.LocationShortSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.OrganizationSyncEntity
import com.example.union_sync_impl.entity.InventoryDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.InventoryDtoV2

fun InventoryDtoV2.toInventoryDb(): InventoryDb {
    return InventoryDb(
        id = id,
        locationIds = listOf(locationId.orEmpty()),
        organizationId = organizationId,
        employeeId = molId,
        date = getMillisDateFromServerFormat(creationDate.orEmpty()) ?: System.currentTimeMillis(),
        updateDate = System.currentTimeMillis(),
        inventoryStatus = extendedInventoryState?.id.orEmpty()
    )
}

fun InventoryDb.toInventoryDtoV2(): InventoryDtoV2 {
    return InventoryDtoV2(
        locationId = locationIds?.lastOrNull(),
        organizationId = organizationId.orEmpty(),
        molId = employeeId,
        inventoryStateId = inventoryStatus,
        inventoryTypeId = "",
        creationDate = getStringDateFromMillis(date),
        dateUpdate = getStringDateFromMillis(System.currentTimeMillis()),
        id = id,
        deleted = false
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
    locationSyncEntities: List<LocationSyncEntity>?,
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
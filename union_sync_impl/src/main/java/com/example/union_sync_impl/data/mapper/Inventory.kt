package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.*
import com.example.union_sync_impl.entity.InventoryDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.InventoryDtoV2

fun InventoryDtoV2.toInventoryDb(): InventoryDb {
    return InventoryDb(
        id = id,
        locationIds = buildList {
            locationId?.let {
                add(it)
            }
        },
        organizationId = organizationId,
        employeeId = molId,
        date = getMillisDateFromServerFormat(creationDate.orEmpty()),
        updateDate = System.currentTimeMillis(),
        inventoryStatus = extendedInventoryState?.id.orEmpty(),
        name = name,
        code = code,
        userUpdated = userUpdated,
        userInserted = userInserted
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
        deleted = false,
        userInserted = userInserted,
        userUpdated = userUpdated
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
        updateDate = updateDate,
        code = code,
        name = name,
        userUpdated = userUpdated,
        userInserted = userInserted
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
        updateDate = updateDate,
        code = code,
        name = name,
        userUpdated = userUpdated,
        userInserted = userInserted
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
        updateDate = updateDate,
        code = code,
        name = name,
        userInserted = userInserted,
        userUpdated = userUpdated
    )
}
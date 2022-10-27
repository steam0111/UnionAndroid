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
        employeeId = molId,
        creationDate = getMillisDateFromServerFormat(dateInsert),
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        inventoryStatus = extendedInventoryState?.id.orEmpty(),
        name = name,
        code = code,
        userUpdated = userUpdated,
        userInserted = userInserted,
        structuralId = structuralUnitId,
        inventoryBaseId = inventoryBaseId,
        cancel = deleted
    )
}

fun InventoryDb.toInventoryDtoV2(): InventoryDtoV2 {
    return InventoryDtoV2(
        locationId = locationIds?.lastOrNull(),
        structuralUnitId = structuralId.orEmpty(),
        molId = employeeId,
        inventoryStateId = inventoryStatus,
        inventoryTypeId = "",
        creationDate = getStringDateFromMillis(creationDate),
        dateUpdate = getStringDateFromMillis(updateDate),
        id = id,
        deleted = cancel ?: false,
        userInserted = userInserted,
        userUpdated = userUpdated,
        dateInsert = getStringDateFromMillis(insertDate)
    )
}

fun InventoryCreateSyncEntity.toInventoryDb(id: String): InventoryDb {
    return InventoryDb(
        id = id,
        employeeId = employeeId,
        creationDate = System.currentTimeMillis(),
        locationIds = locationIds,
        inventoryStatus = inventoryStatus,
        updateDate = System.currentTimeMillis(),
        code = code,
        name = name,
        userUpdated = userUpdated,
        userInserted = userInserted,
        structuralId = structuralId,
        inventoryBaseId = inventoryBaseId,
        cancel = false
    )
}

fun InventoryUpdateSyncEntity.toInventoryDb(): InventoryDb {
    return InventoryDb(
        id = id,
        employeeId = employeeId,
        creationDate = creationDate,
        locationIds = locationIds,
        inventoryStatus = inventoryStatus,
        updateDate = System.currentTimeMillis(),
        code = code,
        name = name,
        userUpdated = userUpdated,
        userInserted = userInserted,
        structuralId = structuralId,
        inventoryBaseId = inventoryBaseId,
        cancel = false
    )
}

fun InventoryDb.toInventorySyncEntity(
    structuralSyncEntities: List<StructuralSyncEntity>?,
    mol: EmployeeSyncEntity?,
    locationSyncEntities: List<LocationSyncEntity>?,
    accountingObjects: List<AccountingObjectSyncEntity>,
    inventoryBaseSyncEntity: EnumSyncEntity?,
    balanceUnit: List<StructuralSyncEntity>,
    checkers: List<InventoryCheckerSyncEntity>
): InventorySyncEntity {
    return InventorySyncEntity(
        id = id,
        creationDate = creationDate,
        structuralSyncEntities = structuralSyncEntities,
        mol = mol,
        locationSyncEntities = locationSyncEntities,
        accountingObjects = accountingObjects,
        inventoryStatus = inventoryStatus,
        updateDate = updateDate,
        code = code,
        name = name,
        userInserted = userInserted,
        userUpdated = userUpdated,
        inventoryBaseSyncEntity = inventoryBaseSyncEntity,
        balanceUnit = balanceUnit,
        checkers = checkers
    )
}
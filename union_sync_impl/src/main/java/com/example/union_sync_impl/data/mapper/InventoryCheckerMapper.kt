package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.InventoryCheckerSyncEntity
import com.example.union_sync_impl.entity.FullInventoryCheckerDb
import com.example.union_sync_impl.entity.InventoryCheckerDb
import org.openapitools.client.models.InventoryCheckerDto

fun InventoryCheckerDto.toInventoryCheckerDb() = InventoryCheckerDb(inventoryId, employeeId)

fun FullInventoryCheckerDb.toSyncEntity() = InventoryCheckerSyncEntity(
    employeeId = checker.employeeId,
    inventoryId = checker.inventoryId,
    employee = employee.toSyncEntity()
)
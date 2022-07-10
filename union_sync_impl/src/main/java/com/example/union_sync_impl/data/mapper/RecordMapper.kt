package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ActionRecordSyncEntity
import com.example.union_sync_api.entity.ActionRemainsRecordSyncEntity
import com.example.union_sync_api.entity.InventoryRecordSyncEntity
import com.example.union_sync_impl.entity.ActionRecordDb
import com.example.union_sync_impl.entity.ActionRemainsRecordDb
import com.example.union_sync_impl.entity.InventoryRecordDb
import org.openapitools.client.models.ActionRecordDtoV2
import org.openapitools.client.models.ActionRemainsRecordDtoV2
import org.openapitools.client.models.InventoryRecordDtoV2

fun ActionRecordDtoV2.toActionRecordDb() = ActionRecordDb(
    id = id,
    accountingObjectId = accountingObjectId.orEmpty(),
    actionId = actionId.orEmpty(),
    updateDate = System.currentTimeMillis()
)

fun ActionRemainsRecordDtoV2.toActionRemainsRecordDb() = ActionRemainsRecordDb(
    id = id,
    remainId = remainsId.orEmpty(),
    actionId = actionId.orEmpty(),
    updateDate = System.currentTimeMillis()
)

fun InventoryRecordDtoV2.toInventoryRecordDb() = InventoryRecordDb(
    id = id,
    accountingObjectId = accountingObjectId.orEmpty(),
    inventoryId = inventoryId.orEmpty(),
    updateDate = System.currentTimeMillis(),
    inventoryStatus = extendedInventoryRecordStatus?.id.orEmpty()
)

fun ActionRecordDb.toSyncEntity() = ActionRecordSyncEntity(
    id = id,
    accountingObjectId = accountingObjectId,
    actionId = actionId,
    updateDate = updateDate ?: System.currentTimeMillis()
)

fun ActionRemainsRecordDb.toSyncEntity() = ActionRemainsRecordSyncEntity(
    id = id,
    remainId = remainId,
    actionId = actionId,
    updateDate = updateDate ?: System.currentTimeMillis()
)

fun InventoryRecordDb.toSyncEntity() = InventoryRecordSyncEntity(
    id = id,
    accountingObjectId = accountingObjectId,
    inventoryId = inventoryId,
    updateDate = updateDate ?: System.currentTimeMillis()
)
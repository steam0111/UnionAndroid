package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ActionBaseSyncEntity
import com.example.union_sync_api.entity.ActionRecordSyncEntity
import com.example.union_sync_api.entity.ActionRemainsRecordSyncEntity
import com.example.union_sync_api.entity.InventoryRecordSyncEntity
import com.example.union_sync_impl.entity.ActionBaseDb
import com.example.union_sync_impl.entity.ActionRecordDb
import com.example.union_sync_impl.entity.ActionRemainsRecordDb
import com.example.union_sync_impl.entity.InventoryRecordDb
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.ActionBaseDtoV2
import org.openapitools.client.models.ActionRecordDtoV2
import org.openapitools.client.models.ActionRemainsRecordDtoV2
import org.openapitools.client.models.InventoryRecordDtoV2

fun ActionRecordDtoV2.toActionRecordDb() = ActionRecordDb(
    id = id,
    accountingObjectId = accountingObjectId.orEmpty(),
    actionId = actionId.orEmpty(),
    updateDate = System.currentTimeMillis(),
    userUpdated = userUpdated,
    userInserted = userInserted
)

fun ActionRecordDb.toActionRecordDtoV2() = ActionRecordDtoV2(
    id = id,
    accountingObjectId = accountingObjectId,
    actionId = actionId,
    deleted = false,
    dateUpdate = getStringDateFromMillis(System.currentTimeMillis()),
    userInserted = userInserted,
    userUpdated = userUpdated
)

fun ActionRemainsRecordDtoV2.toActionRemainsRecordDb() = ActionRemainsRecordDb(
    id = id,
    remainId = remainsId.orEmpty(),
    actionId = actionId.orEmpty(),
    updateDate = System.currentTimeMillis(),
    count = count,
    userUpdated = userUpdated,
    userInserted = userInserted
)

fun ActionRemainsRecordDb.toActionRemainsRecordDtoV2() = ActionRemainsRecordDtoV2(
    id = id,
    remainsId = remainId,
    actionId = actionId,
    deleted = false,
    count = count,
    dateUpdate = getStringDateFromMillis(System.currentTimeMillis()),
    userInserted = userInserted,
    userUpdated = userUpdated
)

fun InventoryRecordDtoV2.toInventoryRecordDb() = InventoryRecordDb(
    id = id,
    accountingObjectId = accountingObjectId.orEmpty(),
    inventoryId = inventoryId.orEmpty(),
    updateDate = System.currentTimeMillis(),
    inventoryStatus = inventoryRecordStatusId.orEmpty(),
    userUpdated = userUpdated,
    userInserted = userInserted
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

fun InventoryRecordDb.toInventoryRecordDtoV2() = InventoryRecordDtoV2(
    id = id,
    accountingObjectId = accountingObjectId,
    inventoryId = inventoryId,
    deleted = false,
    inventoryRecordStatusId = inventoryStatus,
    dateUpdate = getStringDateFromMillis(System.currentTimeMillis()),
    userInserted = userInserted,
    userUpdated = userUpdated
)

fun ActionBaseDtoV2.toActionBaseDb() = ActionBaseDb(
    id = id.orEmpty(),
    name = name.orEmpty(),
    updateDate = System.currentTimeMillis()
)

fun ActionBaseDb.toSyncEntity() = ActionBaseSyncEntity(
    id = id,
    name = name,
    updateDate = updateDate
)

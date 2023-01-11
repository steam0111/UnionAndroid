package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.ActionRecordSyncEntity
import com.example.union_sync_api.entity.ActionRemainsRecordSyncEntity
import com.example.union_sync_api.entity.InventoryNomenclatureRecordSyncEntity
import com.example.union_sync_api.entity.InventoryRecordSyncEntity
import com.example.union_sync_impl.entity.ActionRecordDb
import com.example.union_sync_impl.entity.ActionRemainsRecordDb
import com.example.union_sync_impl.entity.InventoryNomenclatureRecordDb
import com.example.union_sync_impl.entity.InventoryRecordDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.entity.TransitAccountingObjectRecordDb
import com.example.union_sync_impl.entity.TransitRemainsRecordDb
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.ActionRecordDtoV2
import org.openapitools.client.models.ActionRemainsRecordDtoV2
import org.openapitools.client.models.EnumDtoV2
import org.openapitools.client.models.InventoryNomenclatureRecordDtoV2
import org.openapitools.client.models.InventoryRecordDtoV2
import org.openapitools.client.models.TransitAccountingObjectRecordDtoV2
import org.openapitools.client.models.TransitRemainsRecordDtoV2

fun ActionRecordDtoV2.toActionRecordDb() = ActionRecordDb(
    id = id,
    accountingObjectId = accountingObjectId.orEmpty(),
    actionId = actionId.orEmpty(),
    insertDate = getMillisDateFromServerFormat(dateInsert),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    userUpdated = userUpdated,
    userInserted = userInserted,
    cancel = deleted
)

fun ActionRecordDb.toActionRecordDtoV2() = ActionRecordDtoV2(
    id = id,
    accountingObjectId = accountingObjectId,
    actionId = actionId,
    deleted = cancel ?: false,
    dateUpdate = getStringDateFromMillis(updateDate),
    userInserted = userInserted,
    userUpdated = userUpdated,
    dateInsert = getStringDateFromMillis(insertDate)
)

fun ActionRemainsRecordDtoV2.toActionRemainsRecordDb() = ActionRemainsRecordDb(
    id = id,
    remainId = remainsId.orEmpty(),
    actionId = actionId.orEmpty(),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    insertDate = getMillisDateFromServerFormat(dateInsert),
    count = count,
    userUpdated = userUpdated,
    userInserted = userInserted,
    cancel = deleted
)

fun ActionRemainsRecordDb.toActionRemainsRecordDtoV2() = ActionRemainsRecordDtoV2(
    id = id,
    remainsId = remainId,
    actionId = actionId,
    deleted = cancel ?: false,
    count = count,
    dateUpdate = getStringDateFromMillis(updateDate),
    userInserted = userInserted,
    userUpdated = userUpdated,
    dateInsert = getStringDateFromMillis(insertDate)
)

fun InventoryRecordDtoV2.toInventoryRecordDb() = InventoryRecordDb(
    id = id,
    accountingObjectId = accountingObjectId.orEmpty(),
    inventoryId = inventoryId.orEmpty(),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    insertDate = getMillisDateFromServerFormat(dateInsert),
    inventoryStatus = inventoryRecordStatusId.orEmpty(),
    userUpdated = userUpdated,
    userInserted = userInserted,
    cancel = deleted,
    comment = comment,
    manualInput = manualInput,
)

fun ActionRecordDb.toSyncEntity() = ActionRecordSyncEntity(
    id = id,
    accountingObjectId = accountingObjectId,
    actionId = actionId,
    updateDate = updateDate
)

fun ActionRemainsRecordDb.toSyncEntity() = ActionRemainsRecordSyncEntity(
    id = id,
    remainId = remainId,
    actionId = actionId,
    updateDate = updateDate
)

fun InventoryRecordDb.toSyncEntity() = InventoryRecordSyncEntity(
    id = id,
    accountingObjectId = accountingObjectId,
    inventoryId = inventoryId,
    updateDate = updateDate
)

fun InventoryRecordDb.toInventoryRecordDtoV2() = InventoryRecordDtoV2(
    id = id,
    accountingObjectId = accountingObjectId,
    inventoryId = inventoryId,
    deleted = cancel ?: false,
    inventoryRecordStatusId = inventoryStatus,
    dateUpdate = getStringDateFromMillis(System.currentTimeMillis()),
    userInserted = userInserted,
    userUpdated = userUpdated,
    dateInsert = getStringDateFromMillis(insertDate),
    comment = comment,
    manualInput = manualInput
)

fun TransitRemainsRecordDtoV2.toTransitRemainsDb() = TransitRemainsRecordDb(
    id = id,
    transitId = transitId,
    remainId = remainsId,
    count = count,
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    userUpdated = userUpdated,
    userInserted = userInserted,
    insertDate = getMillisDateFromServerFormat(dateInsert),
    cancel = deleted
)

fun TransitAccountingObjectRecordDtoV2.toTransitAccountingObjectDb() =
    TransitAccountingObjectRecordDb(
        id = id,
        transitId = transitId,
        accountingObjectId = accountingObjectId,
        updateDate = getMillisDateFromServerFormat(dateUpdate),
        userInserted = userInserted,
        userUpdated = userUpdated,
        insertDate = getMillisDateFromServerFormat(dateInsert),
        cancel = deleted
    )

fun TransitRemainsRecordDb.toTransitRemainsDb() = TransitRemainsRecordDtoV2(
    id = id,
    transitId = transitId,
    remainsId = remainId,
    count = count,
    deleted = cancel ?: false
)

fun TransitAccountingObjectRecordDb.toTransitAccountingObjectDb() =
    TransitAccountingObjectRecordDtoV2(
        id = id,
        transitId = transitId,
        accountingObjectId = accountingObjectId,
        deleted = cancel ?: false
    )

fun InventoryNomenclatureRecordDtoV2.toDb() = InventoryNomenclatureRecordDb(
    id = id,
    cancel = deleted,
    inventoryId = inventoryId.orEmpty(),
    nomenclatureId = nomenclatureId.orEmpty(),
    expectedCount = expectedCount,
    actualCount = actualCount,
    consignment = consignment,
    bookKeepingInvoice = bookKeepingInvoice,
    price = price,
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    insertDate = getMillisDateFromServerFormat(dateInsert),
    userInserted = userInserted,
    userUpdated = userUpdated,
)

fun InventoryNomenclatureRecordDb.toDto() = InventoryNomenclatureRecordDtoV2(
    id = id,
    deleted = cancel ?: false,
    inventoryId = inventoryId,
    nomenclatureId = nomenclatureId,
    expectedCount = expectedCount,
    actualCount = actualCount,
    consignment = consignment,
    bookKeepingInvoice = bookKeepingInvoice,
    price = price,
    dateUpdate = getStringDateFromMillis(updateDate),
    dateInsert = getStringDateFromMillis(insertDate),
    userInserted = userInserted,
    userUpdated = userUpdated,
)

fun InventoryNomenclatureRecordDb.toSyncEntity() = InventoryNomenclatureRecordSyncEntity(
    id = id,
    inventoryId = inventoryId,
    nomenclatureId = nomenclatureId,
    updateDate = updateDate,
    expectedCount = expectedCount,
    actualCount = actualCount,
    consignment = consignment,
    bookKeepingInvoice = bookKeepingInvoice,
    price = price,
)
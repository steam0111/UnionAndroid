package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.TerminalRemainsNumeratorSyncEntity
import com.example.union_sync_api.entity.UpdateTerminalRemainsNumerator
import com.example.union_sync_impl.entity.TerminalRemainsNumeratorActualUpdate
import com.example.union_sync_impl.entity.TerminalRemainsNumeratorDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.TerminalRemainsNumeratorDtoV2

fun TerminalRemainsNumeratorDtoV2.toDb() = TerminalRemainsNumeratorDb(
    terminalPrefix = terminalPrefix,
    terminalId = terminalId,
    actualNumber = actualNumber,
    insertDate = getMillisDateFromServerFormat(dateInsert),
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    userInserted = userInserted,
    userUpdated = userUpdated,
    cancel = deleted,
    id = remainsId
)

fun TerminalRemainsNumeratorDb.toSyncEntity() = TerminalRemainsNumeratorSyncEntity(
    terminalPrefix = terminalPrefix,
    terminalId = terminalId,
    remainsId = id,
    actualNumber = actualNumber,
)

fun TerminalRemainsNumeratorSyncEntity.toDb(userInserted: String?) = TerminalRemainsNumeratorDb(
    terminalPrefix = terminalPrefix,
    terminalId = terminalId,
    id = remainsId,
    actualNumber = actualNumber,
    insertDate = System.currentTimeMillis(),
    updateDate = System.currentTimeMillis(),
    userInserted = userInserted,
    userUpdated = null
)

fun TerminalRemainsNumeratorDb.toTerminalRemainsNumeratorDtoV2() = TerminalRemainsNumeratorDtoV2(
    remainsId = id,
    terminalPrefix = terminalPrefix,
    terminalId = terminalId,
    actualNumber = actualNumber,
    dateInsert = getStringDateFromMillis(insertDate),
    dateUpdate = getStringDateFromMillis(updateDate),
    userInserted = userInserted,
    userUpdated = userUpdated,
    deleted = cancel ?: false,
)

fun UpdateTerminalRemainsNumerator.toUpdate() = TerminalRemainsNumeratorActualUpdate(
    actualNumber = actualNumber,
    id = remainsId
)
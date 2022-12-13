package com.itrocket.union.terminalRemainsNumerator.data

import com.example.union_sync_api.entity.TerminalRemainsNumeratorSyncEntity
import com.itrocket.union.terminalRemainsNumerator.domain.TerminalRemainsNumeratorDomain

fun TerminalRemainsNumeratorSyncEntity.toDomain() = TerminalRemainsNumeratorDomain(
    terminalPrefix = terminalPrefix,
    terminalId = terminalId,
    remainsId = remainsId,
    actualNumber = actualNumber,
)

fun TerminalRemainsNumeratorDomain.toSyncEntity() = TerminalRemainsNumeratorSyncEntity(
    terminalPrefix = terminalPrefix,
    terminalId = terminalId,
    remainsId = remainsId,
    actualNumber = actualNumber,
)
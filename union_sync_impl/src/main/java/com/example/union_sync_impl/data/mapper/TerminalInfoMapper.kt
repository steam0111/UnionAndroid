package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.TerminalInfoSyncEntity
import com.example.union_sync_impl.entity.TerminalInfoDb

fun TerminalInfoDb.toSyncEntity() = TerminalInfoSyncEntity(terminalPrefix = terminalPrefix)
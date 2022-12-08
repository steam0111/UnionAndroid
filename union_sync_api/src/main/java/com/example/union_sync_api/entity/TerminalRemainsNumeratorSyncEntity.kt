package com.example.union_sync_api.entity

data class TerminalRemainsNumeratorSyncEntity(
    val terminalPrefix: Int,
    val terminalId: String,
    val remainsId: String,
    val actualNumber: Int,
)
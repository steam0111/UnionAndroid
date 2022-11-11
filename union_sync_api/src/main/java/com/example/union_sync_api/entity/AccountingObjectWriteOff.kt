package com.example.union_sync_api.entity

data class AccountingObjectWriteOff(
    val id: String,
    val status: EnumSyncEntity?,
    val writtenOff: Boolean,
)
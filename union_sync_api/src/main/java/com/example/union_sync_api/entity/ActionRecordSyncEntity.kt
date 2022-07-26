package com.example.union_sync_api.entity

data class ActionRecordSyncEntity(
    val id: String,
    val actionId: String,
    val accountingObjectId: String,
    val updateDate: Long
)
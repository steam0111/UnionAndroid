package com.example.union_sync_api.entity

data class ActionRemainsRecordSyncEntity(
    val id: String,
    val actionId: String,
    val remainId: String,
    val updateDate: Long?
)
package com.example.union_sync_api.entity

data class AccountingObjectInfoSyncEntity(
    val id: String,
    val status: String,
    val comment: String?,
    val manualInput: Boolean?
)
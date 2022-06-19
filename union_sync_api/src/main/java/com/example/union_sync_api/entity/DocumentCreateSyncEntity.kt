package com.example.union_sync_api.entity

data class DocumentCreateSyncEntity(
    val organizationId: String,
    val molId: String,
    val exploitingId: String?,
    val documentType: String,
    val locationIds: List<String>?,
    val accountingObjectsIds: List<String>
)
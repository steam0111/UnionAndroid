package com.example.union_sync_api.entity

data class DocumentCreateSyncEntity(
    val organizationId: String,
    val molId: String,
    val exploitingId: String? = null,
    val documentType: String,
    val locationIds: List<String>? = null,
    val accountingObjectsIds: List<String>
)
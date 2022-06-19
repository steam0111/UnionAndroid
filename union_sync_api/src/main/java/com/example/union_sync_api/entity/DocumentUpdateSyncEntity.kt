package com.example.union_sync_api.entity

data class DocumentUpdateSyncEntity(
    val id: Long,
    val organizationId: String,
    val molId: String,
    val exploitingId: String? = null,
    val documentType: String,
    val accountingObjectsIds: List<String>,
    val date: Long
)
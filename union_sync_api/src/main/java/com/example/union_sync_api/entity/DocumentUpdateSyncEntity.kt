package com.example.union_sync_api.entity

data class DocumentUpdateSyncEntity(
    val id: Long,
    val organizationId: String,
    val molId: String,
    val exploitingId: String? = null,
    val documentType: String,
    val accountingObjectsIds: List<String>? = null,
    val locationIds: List<String>? = null,
    val date: Long,
    val reservesIds: List<String>? = null,
    val objectType: String
)
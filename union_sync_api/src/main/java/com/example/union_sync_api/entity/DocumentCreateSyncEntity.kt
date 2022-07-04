package com.example.union_sync_api.entity

data class DocumentCreateSyncEntity(
    val organizationId: String,
    val molId: String,
    val exploitingId: String? = null,
    val documentType: String,
    val documentStatus: String,
    val documentStatusId: String,
    val creationDate: Long,
    val locationIds: List<String>? = null,
    val accountingObjectsIds: List<String>? = null,
    val reservesIds: List<String>? = null,
    val objectType: String
)
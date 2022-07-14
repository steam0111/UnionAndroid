package com.example.union_sync_api.entity

data class DocumentUpdateSyncEntity(
    val id: String,
    val organizationId: String?,
    val molId: String?,
    val exploitingId: String? = null,
    val documentType: String,
    val accountingObjectsIds: List<String>? = null,
    val locationIds: List<String>? = null,
    val creationDate: Long,
    val completionDate: Long?,
    val documentStatus: String,
    val documentStatusId: String,
    val reservesIds: List<String>? = null,
)
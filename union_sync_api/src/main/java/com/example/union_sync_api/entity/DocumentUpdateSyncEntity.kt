package com.example.union_sync_api.entity

data class DocumentUpdateSyncEntity(
    val id: String,
    val molId: String?,
    val exploitingId: String? = null,
    val documentType: String,
    val accountingObjectsIds: List<String>? = null,
    val locationFromId: String? = null,
    val locationToId: String? = null,
    val actionBaseId: String? = null,
    val creationDate: Long,
    val completionDate: Long?,
    val documentStatus: String,
    val documentStatusId: String,
    val reservesIds: List<DocumentReserveCountSyncEntity>? = null,
    val code: String?,
    val userInserted: String?,
    val userUpdated: String?,
    val structuralToId: String?,
    val structuralFromId: String?
)
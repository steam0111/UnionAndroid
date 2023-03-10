package com.example.union_sync_api.entity

data class DocumentCreateSyncEntity(
    val molId: String,
    val exploitingId: String? = null,
    val documentType: String,
    val documentStatus: String,
    val documentStatusId: String,
    val completionDate: Long?,
    val locationFromId: String? = null,
    val locationToId: String? = null,
    val actionBaseId: String? = null,
    val accountingObjectsIds: List<String>? = null,
    val reservesIds: List<ReserveCountSyncEntity>? = null,
    val code: String?,
    val userInserted: String?,
    val userUpdated: String?,
    val structuralToId: String?,
    val structuralFromId: String?
)
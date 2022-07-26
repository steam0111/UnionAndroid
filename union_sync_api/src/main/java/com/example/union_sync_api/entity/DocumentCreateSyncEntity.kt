package com.example.union_sync_api.entity

data class DocumentCreateSyncEntity(
    val organizationId: String,
    val molId: String,
    val exploitingId: String? = null,
    val documentType: String,
    val documentStatus: String,
    val documentStatusId: String,
    val creationDate: Long,
    val completionDate: Long?,
    val locationFromId: String? = null,
    val locationToId: String? = null,
    val departmentFromId: String? = null,
    val departmentToId: String? = null,
    val actionBaseId: String? = null,
    val branchId: String? = null,
    val accountingObjectsIds: List<String>? = null,
    val reservesIds: List<DocumentReserveCountSyncEntity>? = null,
    val code: String?
)
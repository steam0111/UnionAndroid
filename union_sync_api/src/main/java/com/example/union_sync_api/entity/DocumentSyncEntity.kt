package com.example.union_sync_api.entity

data class DocumentSyncEntity(
    val documentType: String,
    val accountingObjects: List<AccountingObjectSyncEntity>,
    val id: String,
    val structuralFromSyncEntity: StructuralSyncEntity?,
    val structuralToSyncEntity: StructuralSyncEntity?,
    val mol: EmployeeSyncEntity?,
    val exploiting: EmployeeSyncEntity? = null,
    val creationDate: Long?,
    val completionDate: Long? = null,
    val documentStatus: String,
    val documentStatusId: String,
    val reserves: List<ReserveSyncEntity>,
    val locationFrom: LocationSyncEntity? = null,
    val locationTo: LocationSyncEntity? = null,
    val actionBase: ActionBaseSyncEntity? = null,
    val code: String?,
    val userInserted: String?,
    val userUpdated: String?
)
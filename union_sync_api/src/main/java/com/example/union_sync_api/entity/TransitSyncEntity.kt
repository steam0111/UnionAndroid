package com.example.union_sync_api.entity

data class TransitSyncEntity(
    val id: String = "",
    val updateDate: Long?,
    val accountingObjects: List<AccountingObjectSyncEntity>,
    val reserves: List<ReserveSyncEntity>,
    val vehicle: List<LocationSyncEntity>?,
    val locationFrom: List<LocationSyncEntity>?,
    val locationTo: List<LocationSyncEntity>?,
    val creationDate: Long?,
    val completionDate: Long? = null,
    val transitType: String,
    val transitStatus: String,
    val transitStatusId: String,
    val structuralFromSyncEntities: List<StructuralSyncEntity>?,
    val structuralToSyncEntities: List<StructuralSyncEntity>?,
    val mol: EmployeeSyncEntity?,
    val receiving: EmployeeSyncEntity?,
    val code: String?,
    val userInserted: String?,
    val userUpdated: String?
)
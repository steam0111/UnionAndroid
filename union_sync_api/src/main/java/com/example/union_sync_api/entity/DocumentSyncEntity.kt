package com.example.union_sync_api.entity

data class DocumentSyncEntity(
    val organizationId: String?,
    val documentType: String,
    val accountingObjects: List<AccountingObjectSyncEntity>,
    val id: String,
    val organizationSyncEntity: OrganizationSyncEntity?,
    val mol: EmployeeSyncEntity?,
    val exploiting: EmployeeSyncEntity? = null,
    val locations: List<LocationSyncEntity>? = null,
    val creationDate: Long,
    val completionDate: Long? = null,
    val documentStatus: String,
    val documentStatusId: String,
    val reserves: List<ReserveSyncEntity>,
)
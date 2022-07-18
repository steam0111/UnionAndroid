package com.example.union_sync_api.entity

data class DocumentSyncEntity(
    val organizationId: String?,
    val documentType: String,
    val accountingObjects: List<AccountingObjectSyncEntity>,
    val id: String,
    val organizationSyncEntity: OrganizationSyncEntity?,
    val mol: EmployeeSyncEntity?,
    val exploiting: EmployeeSyncEntity? = null,
    val creationDate: Long,
    val completionDate: Long? = null,
    val documentStatus: String,
    val documentStatusId: String,
    val reserves: List<ReserveSyncEntity>,
    val locationFrom: LocationSyncEntity? = null,
    val locationTo: LocationSyncEntity? = null,
    val departmentFrom: DepartmentSyncEntity? = null,
    val departmentTo: DepartmentSyncEntity? = null,
    val branch: BranchSyncEntity? = null,
    val actionBase: ActionBaseSyncEntity? = null
)
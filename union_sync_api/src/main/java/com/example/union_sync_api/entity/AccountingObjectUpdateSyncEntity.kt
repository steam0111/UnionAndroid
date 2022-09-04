package com.example.union_sync_api.entity

data class AccountingObjectUpdateSyncEntity(
    val id: String,
    val status: EnumSyncEntity?,
    val statusId: String?,
    val exploitingId: String?,
    val locationId: String?,
    val updateDate: Long?,
    val molId: String?,
    val structuralId: String?,
    val userUpdated: String?
)
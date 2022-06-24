package com.union.union_sync_api.entity

import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity

data class InventoryCreateSyncEntity(
    val organizationId: String,
    val employeeId: String?,
    val accountingObjectsIds: List<AccountingObjectInfoSyncEntity>,
    val locationIds: List<String>?
)
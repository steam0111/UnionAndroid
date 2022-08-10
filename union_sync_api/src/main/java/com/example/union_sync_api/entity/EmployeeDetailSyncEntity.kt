package com.example.union_sync_api.entity

data class EmployeeDetailSyncEntity(
    val employee: EmployeeSyncEntity,
    val structuralSyncEntity: StructuralSyncEntity?,
    val balanceUnit: StructuralSyncEntity?
)
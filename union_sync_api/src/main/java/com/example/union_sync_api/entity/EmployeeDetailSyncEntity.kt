package com.example.union_sync_api.entity

data class EmployeeDetailSyncEntity(
    val employee: EmployeeSyncEntity,
    val structuralSyncEntities: List<StructuralSyncEntity>?,
    val balanceUnitSyncEntities: List<StructuralSyncEntity>?
)
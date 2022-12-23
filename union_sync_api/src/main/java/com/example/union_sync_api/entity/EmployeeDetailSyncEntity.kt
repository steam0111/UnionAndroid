package com.example.union_sync_api.entity

data class EmployeeDetailSyncEntity(
    val employee: EmployeeSyncEntity,
    val structuralSyncEntities: List<StructuralSyncEntity>? = null,
    val balanceUnitSyncEntities: List<StructuralSyncEntity>? = null,
    val employeeStatusSyncEntity: EnumSyncEntity? = null,
    val workPlaces: List<EmployeeWorkPlaceSyncEntity>? = null
)
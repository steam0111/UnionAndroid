package com.example.union_sync_api.entity

class EmployeeWorkPlaceSyncEntity(
    val locationId: String,
    val catalogItemName: String,
    val employeeId: String,
    val comment: String?,
    val mainWorkPlace: Boolean,
    val location: LocationShortSyncEntity?
)
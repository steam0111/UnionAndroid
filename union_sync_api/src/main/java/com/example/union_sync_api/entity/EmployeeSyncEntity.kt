package com.example.union_sync_api.entity

class EmployeeSyncEntity(
    val id: String,
    val catalogItemName: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val organizationId: String?,
    val number: String,
    val nfc: String?
)
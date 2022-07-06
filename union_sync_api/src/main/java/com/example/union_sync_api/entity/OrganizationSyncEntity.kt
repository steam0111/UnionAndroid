package com.example.union_sync_api.entity

data class OrganizationSyncEntity(
    val id: String,
    val name: String,
    val catalogItemName: String,
    val actualAddress: String?,
    val legalAddress: String?,
    val inn: String?,
    val kpp: String?,
    val comment: String?,
    val employeeId: String?
)
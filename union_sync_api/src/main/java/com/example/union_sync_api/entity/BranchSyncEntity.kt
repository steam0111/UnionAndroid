package com.example.union_sync_api.entity

data class BranchSyncEntity(
    val id: String,
    val catalogItemName: String,
    val organizationId: String?,
    val name: String?,
    val code: String?,
)
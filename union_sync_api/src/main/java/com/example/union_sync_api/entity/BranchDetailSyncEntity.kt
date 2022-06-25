package com.example.union_sync_api.entity

data class BranchDetailSyncEntity(
    val branch: BranchSyncEntity,
    val organization: OrganizationSyncEntity?
)
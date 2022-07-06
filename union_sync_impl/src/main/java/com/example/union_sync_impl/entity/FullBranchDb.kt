package com.example.union_sync_impl.entity

import androidx.room.Embedded

data class FullBranchDb(
    @Embedded
    val branch: BranchesDb,
    @Embedded(prefix = "organizations_")
    val organization: OrganizationDb
)
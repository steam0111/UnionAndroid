package com.example.union_sync_api.data

import com.example.union_sync_api.entity.BranchDetailSyncEntity
import com.example.union_sync_api.entity.BranchSyncEntity

interface BranchesSyncApi {
    suspend fun getBranches(
        organizationId: String? = null,
        textQuery: String? = null
    ): List<BranchSyncEntity>

    suspend fun getBranchesCount(
        organizationId: String? = null,
        textQuery: String? = null
    ): Long

    suspend fun getBranchDetail(id: String): BranchDetailSyncEntity
}
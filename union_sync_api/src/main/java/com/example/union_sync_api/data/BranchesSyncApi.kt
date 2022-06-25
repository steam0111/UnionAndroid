package com.example.union_sync_api.data

import com.example.union_sync_api.entity.BranchDetailSyncEntity
import com.example.union_sync_api.entity.BranchSyncEntity
import kotlinx.coroutines.flow.Flow

interface BranchesSyncApi {
    suspend fun getBranches(): Flow<List<BranchSyncEntity>>

    suspend fun getBranchDetail(id: String): BranchDetailSyncEntity
}
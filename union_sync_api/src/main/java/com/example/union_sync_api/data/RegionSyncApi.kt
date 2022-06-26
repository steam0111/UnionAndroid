package com.example.union_sync_api.data

import com.example.union_sync_api.entity.RegionDetailSyncEntity
import com.example.union_sync_api.entity.RegionSyncEntity
import kotlinx.coroutines.flow.Flow

interface RegionSyncApi {
    suspend fun getRegions(organizationId: String? = null): Flow<List<RegionSyncEntity>>

    suspend fun getRegionDetail(id: String): RegionDetailSyncEntity
}
package com.example.union_sync_api.data

import com.example.union_sync_api.entity.RegionDetailSyncEntity
import com.example.union_sync_api.entity.RegionSyncEntity

interface RegionSyncApi {
    suspend fun getRegions(
        organizationId: String? = null,
        textQuery: String? = null
    ): List<RegionSyncEntity>

    suspend fun getRegionsCount(
        organizationId: String? = null,
        textQuery: String? = null
    ): Long

    suspend fun getRegionDetail(id: String): RegionDetailSyncEntity
}
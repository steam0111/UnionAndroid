package com.example.union_sync_api.data

import com.example.union_sync_api.entity.LocationSyncEntity

interface LocationSyncApi {
    /**
     * Если locationParentId == null то возвращается корневой список
     */
    suspend fun getLocations(locationTypeParentId: String? = null, textQuery: String? = null): List<LocationSyncEntity>
}
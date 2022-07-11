package com.example.union_sync_api.data

import com.example.union_sync_api.entity.LocationSyncEntity

interface LocationSyncApi {
    /**
     * Если locationParentId == null то возвращается корневой список
     */
    suspend fun getLocations(
        locationTypeId: String? = null,
        locationId: String? = null,
        textQuery: String? = null
    ): List<LocationSyncEntity>

    suspend fun getAllLocationsIdsByParentId(parentId: String?): List<String?>

    suspend fun getLocationById(locationId: String): LocationSyncEntity
}
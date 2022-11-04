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

    suspend fun getLocationById(locationId: String?): LocationSyncEntity?

    suspend fun getLocationsByIds(ids: List<String>): List<LocationSyncEntity>

    /**
     * Метод для получения полного пути до childId локации
     **/
    suspend fun getLocationFullPath(
        childId: String?,
        locations: MutableList<LocationSyncEntity> = mutableListOf()
    ): List<LocationSyncEntity>?
}
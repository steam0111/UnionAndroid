package com.example.union_sync_impl.data

import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity

class LocationSyncApiImpl(
    private val locationDao: LocationDao,
) : LocationSyncApi {

    override suspend fun getLocations(
        locationTypeParentId: String?,
        textQuery: String?
    ): List<LocationSyncEntity> {
        val locationType = locationDao.getNextLocationTypeByParent(locationTypeParentId)
        val locations = locationDao.getLocationsByType(locationType.id)

        return locations.map {
            it.toLocationSyncEntity(locationType)
        }
    }
}
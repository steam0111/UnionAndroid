package com.example.union_sync_impl.data

import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.sqlLocationsQuery
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity

class LocationSyncApiImpl(
    private val locationDao: LocationDao,
) : LocationSyncApi {

    override suspend fun getLocations(
        locationTypeId: String?,
        locationId: String?,
        textQuery: String?
    ): List<LocationSyncEntity> {
        val locationType = locationDao.getNextLocationTypeByParent(locationTypeId)
        val locations = locationDao.getLocationsByParentId(
            sqlLocationsQuery(
                parentId = locationId,
                textQuery = if (textQuery.isNullOrEmpty()) {
                    null
                } else {
                    textQuery
                }
            )
        )
        return locations.map {
            it.toLocationSyncEntity(locationType)
        }
    }

    override suspend fun getAllLocationsIdsByParentId(parentId: String?): List<String?> {
        return buildList {
            add(parentId)
            addAll(locationDao.getAllLocationsByParentId(parentId).map { it.id })
        }
    }

    override suspend fun getLocationById(locationId: String): LocationSyncEntity {
        val locationDb = locationDao.getLocationById(locationId)
        val locationType =
            locationDao.getLocationTypeById(requireNotNull(locationDb.locationTypeId))
        return locationDao.getLocationById(locationId)
            .toLocationSyncEntity(locationType)
    }
}
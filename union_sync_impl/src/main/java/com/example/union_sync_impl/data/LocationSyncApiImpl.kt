package com.example.union_sync_impl.data

import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.StructuralSyncEntity
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
            addAll(
                locationDao.getLocationsByParentId(sqlLocationsQuery(parentId = parentId))
                    .map { it.id })
        }
    }

    override suspend fun getLocationFullPath(
        childId: String?,
        locations: MutableList<LocationSyncEntity>
    ): List<LocationSyncEntity>? {
        val childLocation = locationDao.getLocationById(childId)
        val childLocationType =
            locationDao.getLocationTypeById(childLocation?.locationTypeId ?: return locations)
        childLocation.let {
            locations.add(it.toLocationSyncEntity(childLocationType))
        }
        return if (childLocation.parentId == null) {
            locations.reversed()
        } else {
            getLocationFullPath(childLocation.parentId, locations)
        }
    }

    override suspend fun getLocationById(locationId: String?): LocationSyncEntity? {
        val locationDb = locationDao.getLocationById(locationId)
        val locationType = locationDb?.locationTypeId?.let { locationDao.getLocationTypeById(it) }
        return locationDao.getLocationById(locationId)?.toLocationSyncEntity(locationType)
    }

    override suspend fun getLocationsByIds(ids: List<String?>): List<LocationSyncEntity> {
        return locationDao.getLocationsByIds(ids).map {
            val locationType = locationDao.getLocationTypeById(requireNotNull(it.locationTypeId))
            it.toLocationSyncEntity(locationType)
        }
    }
}
package com.example.union_sync_impl.data

import android.util.Log
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
            addAll(locationDao.getAllLocationsByParentId(parentId).map { it.id })
        }
    }

    override suspend fun getLocationFullPath(
        childId: String?,
        locations: MutableList<LocationSyncEntity>
    ): List<LocationSyncEntity>? {
        val childLocation = locationDao.getLocationById(childId)
        val childLocationType =
            locationDao.getLocationTypeById(
                childLocation?.locationDb?.locationTypeId ?: return locations
            )
        childLocation.let {
            locations.add(it.locationDb.toLocationSyncEntity(childLocationType))
        }
        return if (childLocation.locationDb.parentId == null) {
            locations.reversed()
        } else {
            getLocationFullPath(childLocation.locationDb.parentId, locations)
        }
    }

    override suspend fun getLocationById(locationId: String?): LocationSyncEntity? {
        val fullLocationDb = locationDao.getLocationById(locationId)
        return fullLocationDb?.locationDb?.toLocationSyncEntity(fullLocationDb.locationTypeDb)
    }

    override suspend fun getLocationsByIds(ids: List<String>): List<LocationSyncEntity> {
        return locationDao.getLocationsByIds(ids).map { fullLocationDb ->
            fullLocationDb.locationDb.toLocationSyncEntity(fullLocationDb.locationTypeDb)
        }
    }
}
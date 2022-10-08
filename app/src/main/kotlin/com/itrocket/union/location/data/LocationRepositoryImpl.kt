package com.itrocket.union.location.data

import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.entity.LocationSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.location.data.mapper.toLocationDomain
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.location.domain.entity.LocationDomain
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val locationSyncApi: LocationSyncApi,
    private val coreDispatchers: CoreDispatchers
) : LocationRepository {

    override suspend fun getPlaceList(
        selectedPlace: LocationDomain?,
        searchText: String
    ): List<LocationDomain> = withContext(coreDispatchers.io) {
        getLocations(selectedPlace, textQuery = searchText)
    }

    override suspend fun getLocationById(locationId: String): LocationSyncEntity? =
        withContext(coreDispatchers.io) {
            locationSyncApi.getLocationById(locationId)
        }

    override suspend fun getAllLocationsIdsByParent(parentId: String?): List<String?> =
        withContext(coreDispatchers.io) {
            locationSyncApi.getAllLocationsIdsByParentId(parentId)
        }

    private suspend fun getLocations(
        selectedPlace: LocationDomain?,
        textQuery: String?
    ): List<LocationDomain> {
        return locationSyncApi.getLocations(
            locationId = selectedPlace?.id,
            locationTypeId = selectedPlace?.locationTypeId,
            textQuery = textQuery
        ).map {
            it.toLocationDomain()
        }
    }
}
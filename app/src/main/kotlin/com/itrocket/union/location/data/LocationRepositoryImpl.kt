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
        selectedPlaceScheme: List<LocationDomain>,
        searchText: String
    ): List<LocationDomain> = withContext(coreDispatchers.io) {
        getLocations(selectedPlaceScheme, textQuery = searchText)
    }

    override suspend fun getLocationById(locationId: String): LocationSyncEntity =
        withContext(coreDispatchers.io) {
            locationSyncApi.getLocationById(locationId)
        }

    private suspend fun getLocations(
        selectedPlaceScheme: List<LocationDomain>,
        textQuery: String?
    ): List<LocationDomain> {
        return locationSyncApi.getLocations(
            selectedPlaceScheme.firstOrNull()?.locationTypeId,
            textQuery
        ).map {
            it.toLocationDomain()
        }
    }
}
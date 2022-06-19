package com.itrocket.union.location.data

import com.example.union_sync_api.data.LocationSyncApi
import com.itrocket.union.location.data.mapper.toLocationDomain
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.location.domain.entity.LocationDomain

class LocationRepositoryImpl(
    private val locationSyncApi: LocationSyncApi
) : LocationRepository {
    override suspend fun getPlaceList(
        selectedPlaceScheme: List<LocationDomain>,
        searchText: String
    ): List<LocationDomain> {
        return getLocations(selectedPlaceScheme)
    }

    private suspend fun getLocations(
        selectedPlaceScheme: List<LocationDomain>
    ): List<LocationDomain> {
        return locationSyncApi.getLocations(selectedPlaceScheme.firstOrNull()?.locationTypeId).map {
            it.toLocationDomain()
        }
    }
}
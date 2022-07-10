package com.itrocket.union.location.domain.dependencies

import com.example.union_sync_api.entity.LocationSyncEntity
import com.itrocket.union.location.domain.entity.LocationDomain

interface LocationRepository {
    suspend fun getPlaceList(
        selectedPlaceScheme: List<LocationDomain>,
        searchText: String
    ): List<LocationDomain>

    suspend fun getLocationById(locationId: String): LocationSyncEntity
}
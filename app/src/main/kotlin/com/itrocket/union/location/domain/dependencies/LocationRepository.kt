package com.itrocket.union.location.domain.dependencies

import com.itrocket.union.location.domain.entity.LocationDomain

interface LocationRepository {
    suspend fun getPlaceList(
        selectedPlaceScheme: List<LocationDomain>,
        searchText: String
    ): List<LocationDomain>
}
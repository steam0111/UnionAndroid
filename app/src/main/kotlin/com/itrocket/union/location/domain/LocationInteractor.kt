package com.itrocket.union.location.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.location.domain.entity.LocationDomain
import com.itrocket.utils.resolveItem
import kotlinx.coroutines.withContext

class LocationInteractor(
    private val repository: LocationRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getPlaceList(selectedPlace: LocationDomain? = null, searchText: String = "") =
        withContext(coreDispatchers.io) {
            repository.getPlaceList(selectedPlace, searchText)
        }

    fun isNewPlaceList(newList: List<LocationDomain>, oldList: List<LocationDomain>): Boolean {
        return newList.isNotEmpty() && !newList.containsAll(oldList)
    }

    fun resolveNewPlace(
        selectedPlaceScheme: List<LocationDomain>,
        selectedPlace: LocationDomain,
        isRemoveLast: Boolean
    ): List<LocationDomain> {
        val places = selectedPlaceScheme.toMutableList()
        if (isRemoveLast) {
            places.removeLastOrNull()
        }
        places.resolveItem(selectedPlace)
        return places
    }

    fun removeLastPlace(
        selectedPlaceScheme: List<LocationDomain>
    ): List<LocationDomain> {
        val places = selectedPlaceScheme.toMutableList()
        if (places.isNotEmpty()) {
            places.removeLast()
        }
        return places
    }
}
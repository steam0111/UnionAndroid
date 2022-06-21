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

    suspend fun getPlaceList(selectedPlaceScheme: List<LocationDomain>, searchText: String = "") =
        withContext(coreDispatchers.io) {
            repository.getPlaceList(selectedPlaceScheme, searchText)
        }

    fun getPrevPlaceScheme(selectedPlaceScheme: List<LocationDomain>): List<LocationDomain> {
        val prevSelectedPlaceScheme = selectedPlaceScheme.toMutableList()
        prevSelectedPlaceScheme.removeLastOrNull()
        return prevSelectedPlaceScheme
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

    fun placeSchemaToString(schema: List<LocationDomain>): String {
        val location = StringBuilder()
        schema.forEachIndexed { index, locationDomain ->
            if (index < schema.lastIndex) {
                location.append("${locationDomain.value}, ")
            } else {
                location.append(locationDomain.value)
            }
        }
        return location.trim().toString()
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
package com.itrocket.union.location.data

import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.location.domain.entity.LocationDomain

class LocationRepositoryImpl : LocationRepository {
    override suspend fun getPlaceList(
        selectedPlaceScheme: List<LocationDomain>,
        searchText: String
    ): List<LocationDomain> {
        return getMockList(selectedPlaceScheme).filter {
            it.value.contains(searchText)
        }
    }

    private fun getMockList(
        selectedPlaceScheme: List<LocationDomain>
    ): List<LocationDomain> {
        return when {
            selectedPlaceScheme.isEmpty() -> listOf(
                LocationDomain("территория", "Головной офис"),
                LocationDomain("территория", "ДО МиМО"),
                LocationDomain("территория", "Региональная сеть")
            )
            selectedPlaceScheme.size == 1 -> listOf(
                LocationDomain("здание", "ул.Стромынка, д.18, стр.27"),
                LocationDomain("здание", "ул.Стромынка, д.18, стр.28"),
                LocationDomain("здание", "ул.Стромынка, д.18, стр.29"),
                LocationDomain("здание", "ул.Стромынка, д.21, стр.30"),
                LocationDomain("здание", "ул.Стромынка, д.18, стр.31"),
                LocationDomain("здание", "ул.Стромынка, д.18, стр.18"),
                LocationDomain("здание", "ул.Стромынка, д.15, стр.21"),
                LocationDomain("здание", "ул.Стромынка, д.20, стр.44"),
            )
            selectedPlaceScheme.size == 2 -> listOf(
                LocationDomain("этаж и кабинет", "1"),
                LocationDomain("этаж и кабинет", "2"),
                LocationDomain("этаж и кабинет", "3")
            )
            selectedPlaceScheme.size == 3 -> listOf(
                LocationDomain("РМ", "1"),
                LocationDomain("РМ", "2"),
                LocationDomain("РМ", "3")
            )
            else -> listOf()
        }
    }
}
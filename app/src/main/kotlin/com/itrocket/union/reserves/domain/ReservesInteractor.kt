package com.itrocket.union.reserves.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.coroutines.withContext

class ReservesInteractor(
    private val repository: ReservesRepository,
    private val coreDispatchers: CoreDispatchers,
    private val locationRepository: LocationRepository
) {

    suspend fun getReserves(
        searchText: String,
        params: List<ParamDomain>,
        selectedReservesIds: List<String> = listOf()
    ): List<ReservesDomain> =
        withContext(coreDispatchers.io) {
            val lastLocationId = params.getFilterLocationLastId()
            val filterLocationIds = if (lastLocationId == null) {
                null
            } else {
                locationRepository.getAllLocationsIdsByParent(lastLocationId)
            }
            val reserves = repository.getReserves(
                textQuery = searchText,
                params = params,
                selectedLocationIds = filterLocationIds
            ).filter {
                !selectedReservesIds.contains(it.id) && it.itemsCount != 0L
            }
            reserves
        }

    fun getFilters() = listOf(
        ParamDomain(
            type = ManualType.ORGANIZATION,
            value = ""
        ),
        ParamDomain(
            type = ManualType.MOL,
            value = ""
        ),
        LocationParamDomain(),
        ParamDomain(
            type = ManualType.DEPARTMENT,
            value = ""
        ),
        ParamDomain(
            type = ManualType.NOMENCLATURE_GROUP,
            value = ""
        ),
        ParamDomain(
            type = ManualType.RECEPTION_CATEGORY,
            value = ""
        ),
    )
}
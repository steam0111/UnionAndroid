package com.itrocket.union.reserves.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.location.domain.dependencies.LocationRepository
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.manual.getFilterLocationLastId
import com.itrocket.union.manual.getFilterStructuralLastId
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.structural.domain.dependencies.StructuralRepository
import kotlinx.coroutines.withContext

class ReservesInteractor(
    private val repository: ReservesRepository,
    private val coreDispatchers: CoreDispatchers,
    private val locationRepository: LocationRepository,
    private val structuralRepository: StructuralRepository
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
            val lastStructuralId = params.getFilterStructuralLastId()
            val filterStructuralIds = if (lastStructuralId == null) {
                null
            } else {
                structuralRepository.getAllStructuralsIdsByParent(lastStructuralId)
            }
            val reserves = repository.getReserves(
                textQuery = searchText,
                params = params,
                selectedLocationIds = filterLocationIds,
                structuralIds = filterStructuralIds
            ).filter {
                !selectedReservesIds.contains(it.id) && it.itemsCount != 0L
            }
            reserves
        }

    fun getFilters() = listOf(
        StructuralParamDomain(),
        ParamDomain(
            type = ManualType.MOL,
            value = ""
        ),
        LocationParamDomain(),
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
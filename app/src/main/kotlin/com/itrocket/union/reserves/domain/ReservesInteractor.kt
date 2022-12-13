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
import com.itrocket.union.manual.CheckBoxParamDomain
import com.itrocket.union.manual.getFilterHideZeroReserves
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
        selectedReservesIds: List<String> = listOf(),
        offset: Long? = null,
        limit: Long? = null
    ): List<ReservesDomain> =
        withContext(coreDispatchers.io) {
            val lastLocationId = params.getFilterLocationLastId()
            val filterLocationIds = if (lastLocationId == null) {
                null
            } else {
                locationRepository.getAllLocationsIdsByParent(lastLocationId)
            }
            val lastStructuralId = params.getFilterStructuralLastId(ManualType.STRUCTURAL)
            val filterStructuralIds = if (lastStructuralId == null) {
                null
            } else {
                structuralRepository.getAllStructuralsIdsByParent(lastStructuralId)
            }
            val reserves = repository.getReserves(
                textQuery = searchText,
                params = params,
                selectedLocationIds = filterLocationIds,
                structuralIds = filterStructuralIds,
                offset = offset,
                limit = limit,
                hideZeroReserves = params.getFilterHideZeroReserves()
            ).filter {
                !selectedReservesIds.contains(it.id)
            }
            reserves
        }

    fun getFilters(isFromDocuments: Boolean) = buildList {
        add(StructuralParamDomain(manualType = ManualType.STRUCTURAL))
        add(
            ParamDomain(
                type = ManualType.MOL,
                value = ""
            )
        )
        add(LocationParamDomain())
        add(
            ParamDomain(
                type = ManualType.NOMENCLATURE_GROUP,
                value = ""
            )
        )
        add(
            ParamDomain(
                type = ManualType.RECEPTION_CATEGORY,
                value = ""
            )
        )
        add(
            ParamDomain(
                type = ManualType.NOMENCLATURE_CODE,
                value = ""
            )
        )
        if (isFromDocuments) {
            add(
                CheckBoxParamDomain(
                    isChecked = false,
                    manualType = ManualType.CHECKBOX_HIDE_ZERO_RESERVES
                )
            )
        }
    }
}
package com.itrocket.union.reserves.domain

import kotlinx.coroutines.withContext
import com.itrocket.union.reserves.domain.dependencies.ReservesRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain

class ReservesInteractor(
    private val repository: ReservesRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getReserves(searchText: String, params: List<ParamDomain>): List<ReservesDomain> =
        withContext(coreDispatchers.io) {
            repository.getReserves(textQuery = searchText, params = params)
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
        LocationParamDomain(
            ids = listOf(),
            values = listOf()
        ),
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
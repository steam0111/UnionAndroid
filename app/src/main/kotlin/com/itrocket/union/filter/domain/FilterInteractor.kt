package com.itrocket.union.filter.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.filter.domain.dependencies.FilterRepository
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.Params
import com.itrocket.utils.resolveItem

class FilterInteractor(
    private val repository: FilterRepository,
    private val coreDispatchers: CoreDispatchers
) {
    fun getFilters() = repository.getFilters()

    fun dropFilterFields(filters: List<ParamDomain>): List<ParamDomain> {
        return filters.toMutableList().map {
            it.copy(value = "", id = null)
        }
    }

    fun getDefaultTypeParams(params: Params): List<ParamDomain> {
        return params.paramList.filter {
            params.isDefaultParamType(it)
        }
    }

    fun changeFilters(
        filters: List<ParamDomain>,
        newFilters: List<ParamDomain>
    ): List<ParamDomain> {
        val mutableFilters = filters.toMutableList()
        mutableFilters.forEachIndexed { index, paramDomain ->
            val newParam = newFilters.find { it.type == paramDomain.type }
            if (newParam != null) {
                mutableFilters[index] = newParam
            }
        }
        return mutableFilters
    }

    fun changeLocationFilter(
        filters: List<ParamDomain>,
        location: LocationParamDomain
    ): List<ParamDomain> {
        val mutableFilters = filters.toMutableList()
        val locationIndex = filters.indexOfFirst { it.type == ManualType.LOCATION }
        if (locationIndex != NO_POSITION) {
            mutableFilters[locationIndex] =
                (mutableFilters[locationIndex] as LocationParamDomain).copy(
                    values = location.values
                )
        }
        return mutableFilters
    }

    companion object {
        private const val NO_POSITION = -1
    }
}
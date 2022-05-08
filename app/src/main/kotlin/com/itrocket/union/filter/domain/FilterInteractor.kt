package com.itrocket.union.filter.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.filter.domain.dependencies.FilterRepository
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.domain.entity.FilterValueType
import com.itrocket.utils.resolveItem

class FilterInteractor(
    private val repository: FilterRepository,
    private val coreDispatchers: CoreDispatchers
) {
    fun getFilters() = repository.getFilters()

    fun updateSingleFilterValues(
        filterValues: List<String>,
        newFilterValue: String,
    ): List<String> {
        val newFilterValues = filterValues.toMutableList()
        return if (newFilterValues.contains(newFilterValue)) {
            listOf()
        } else {
            listOf(newFilterValue)
        }
    }

    fun updateFilterValues(
        filterValues: List<String>,
        newFilterValue: String,
    ): List<String> {
        return filterValues.toMutableList().resolveItem(newFilterValue)
    }

    fun dropFilterFields(filters: List<FilterDomain>): List<FilterDomain> {
        return filters.toMutableList().map {
            if (it.filterValueType == FilterValueType.MULTI_SELECT_LIST) {
                it.copy(values = listOf())
            } else {
                it
            }
        }
    }

    fun changeFilter(filters: List<FilterDomain>, filterChange: FilterDomain): List<FilterDomain> {
        val mutableFilters = filters.toMutableList()
        val oldFilter = mutableFilters.find { it.name == filterChange.name }
        val indexFilter = mutableFilters.indexOf(oldFilter)
        mutableFilters[indexFilter] = filterChange
        return mutableFilters
    }

    fun getFinalFilterValues(
        filterValueType: FilterValueType,
        filterValues: List<String>,
        singleValue: String
    ): List<String> {
        return if (filterValueType == FilterValueType.MULTI_SELECT_LIST ||
            filterValueType == FilterValueType.SINGLE_SELECT_LIST
        ) {
            filterValues
        } else {
            listOf(singleValue)
        }
    }

    fun changeLocationFilter(filters: List<FilterDomain>, location: String): List<FilterDomain> {
        val mutableFilters = filters.toMutableList()
        val locationFilter = filters.find { it.filterValueType == FilterValueType.LOCATION }
        val locationIndex = filters.indexOf(locationFilter)
        if (locationIndex != NO_POSITION) {
            mutableFilters[locationIndex] = mutableFilters[locationIndex].copy(
                values = listOf(location)
            )
        }
        return mutableFilters
    }

    companion object {
        private const val NO_POSITION = -1
    }
}
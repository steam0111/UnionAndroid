package com.itrocket.union.filter.domain.dependencies

import com.itrocket.union.filter.domain.entity.FilterDomain

interface FilterRepository {

    fun getFilters(): List<FilterDomain>
}
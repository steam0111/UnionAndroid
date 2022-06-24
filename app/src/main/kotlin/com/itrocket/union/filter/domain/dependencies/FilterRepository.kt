package com.itrocket.union.filter.domain.dependencies

import com.itrocket.union.manual.ParamDomain

interface FilterRepository {

    fun getFilters(): List<ParamDomain>
}
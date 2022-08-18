package com.itrocket.union.filter.data

import com.itrocket.union.filter.domain.dependencies.FilterRepository
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain

class FilterRepositoryImpl : FilterRepository {
    override fun getFilters(): List<ParamDomain> {
        return listOf(
            StructuralParamDomain(manualType = ManualType.STRUCTURAL),
            ParamDomain(
                type = ManualType.MOL,
                value = ""
            ),
            ParamDomain(
                type = ManualType.EXPLOITING,
                value = ""
            ),
            LocationParamDomain(
                locations = listOf()
            ),
            ParamDomain(
                type = ManualType.STATUS,
                value = ""
            ),
        )
    }
}
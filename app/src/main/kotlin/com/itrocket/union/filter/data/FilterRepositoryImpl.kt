package com.itrocket.union.filter.data

import com.itrocket.union.filter.domain.dependencies.FilterRepository
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain

class FilterRepositoryImpl : FilterRepository {
    override fun getFilters(): List<ParamDomain> {
        return listOf(
            ParamDomain(
                id = "Организация",
                type = ManualType.ORGANIZATION,
                value = ""
            ),
            ParamDomain(
                id = "Ответственный",
                type = ManualType.MOL,
                value = ""
            ),
            ParamDomain(
                id = "Эксплуатирующий",
                type = ManualType.EXPLOITING,
                value = ""
            ),
            LocationParamDomain(
                ids = listOf(),
                values = listOf()
            ),
            ParamDomain(
                id = "Статус",
                type = ManualType.STATUS,
                value = ""
            ),
        )
    }
}
package com.itrocket.union.selectParams.domain.dependencies

import com.itrocket.union.manual.ManualType

interface SelectParamsRepository {
    suspend fun getParamValues(type: ManualType, searchText: String): List<String>
}
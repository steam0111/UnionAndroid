package com.itrocket.union.selectParams.domain.dependencies

import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamValueDomain

interface SelectParamsRepository {
    suspend fun getParamValues(type: ManualType, searchText: String): List<ParamValueDomain>

    suspend fun getOrganizationList(): List<ParamValueDomain>
}
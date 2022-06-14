package com.itrocket.union.selectParams.domain.dependencies

import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamValueDomain
import kotlinx.coroutines.flow.Flow

interface SelectParamsRepository {
    suspend fun getParamValues(type: ManualType, searchText: String): Flow<List<ParamValueDomain>>

    suspend fun getOrganizationList(): Flow<List<ParamValueDomain>>
}
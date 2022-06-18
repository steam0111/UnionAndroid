package com.itrocket.union.selectParams.domain.dependencies

import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.flow.Flow

interface SelectParamsRepository {
    suspend fun getParamValues(type: ManualType, searchText: String): Flow<List<ParamDomain>>

    suspend fun getOrganizationList(): Flow<List<ParamDomain>>

    suspend fun getEmployees(type: ManualType): Flow<List<ParamDomain>>
}
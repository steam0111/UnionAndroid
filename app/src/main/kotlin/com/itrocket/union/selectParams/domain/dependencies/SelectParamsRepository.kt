package com.itrocket.union.selectParams.domain.dependencies

interface SelectParamsRepository {
    suspend fun getParamValues(param: String): List<String>
}
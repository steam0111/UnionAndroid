package com.itrocket.union.selectParams.data

import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository

class SelectParamsRepositoryImpl : SelectParamsRepository {

    override suspend fun getParamValues(param: String): List<String> {
        return listOf(
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
            "123321",
        )
    }
}
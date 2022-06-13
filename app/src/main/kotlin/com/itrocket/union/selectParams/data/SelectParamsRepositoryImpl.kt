package com.itrocket.union.selectParams.data

import com.itrocket.union.manual.ManualType
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository

class SelectParamsRepositoryImpl : SelectParamsRepository {

    val paramMockList = listOf(
        "1",
        "12",
        "2",
        "3",
        "123",
        "122",
        "231",
        "4444",
        "1111d",
        "4343",
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

    override suspend fun getParamValues(type: ManualType, searchText: String): List<String> {
        return paramMockList.filter {
            it.lowercase().contains(searchText.lowercase())
        }
    }
}
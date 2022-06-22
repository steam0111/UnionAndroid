package com.itrocket.union.identify.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.identify.domain.dependencies.IdentifyRepository
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.withContext

class IdentifyInteractor (
    private val repository: IdentifyRepository,
    private val coreDispatchers: CoreDispatchers
)
{
    suspend fun getIdentify() = withContext(coreDispatchers.io) {
        repository.getIdentify()
    }

    fun clearParam(list: List<ParamDomain>, param: ParamDomain): List<ParamDomain> {
        val mutableList = list.toMutableList()
        val currentIndex = mutableList.indexOfFirst { it.type == param.type }
        mutableList[currentIndex] = mutableList[currentIndex].copy(id = "", value = "")
        return mutableList
    }

    fun clearParams(list: List<ParamDomain>): List<ParamDomain> {
        return list.map {
            it.copy(id = "", value = "")
        }
    }
}
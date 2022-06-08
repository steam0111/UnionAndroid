package com.itrocket.union.inventory.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain

class InventoryInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers
) {

    fun createInventory(accountingObjects: List<AccountingObjectDomain>) {
        // add request
    }

    fun changeParams(params: List<ParamDomain>, newParams: List<ParamDomain>): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        mutableParams.forEachIndexed { index, paramDomain ->
            val newParam = newParams.find { it.type == paramDomain.type }
            if (newParam != null) {
                mutableParams[index] = newParam
            }
        }
        return mutableParams
    }

    fun changeLocation(params: List<ParamDomain>, location: String): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val locationParam = params.find { it.type == ManualType.LOCATION }
        val locationIndex = params.indexOf(locationParam)
        mutableParams[locationIndex] = mutableParams[locationIndex].copy(value = location)
        return mutableParams
    }

    fun clearParam(list: List<ParamDomain>, param: ParamDomain): List<ParamDomain> {
        val mutableList = list.toMutableList()
        val currentParam = mutableList.find { it.type == param.type }
        val currentIndex = mutableList.indexOf(currentParam)
        mutableList[currentIndex] = mutableList[currentIndex].copy(value = "")
        return mutableList
    }

    fun clearParams(list: List<ParamDomain>): List<ParamDomain> {
        return list.map {
            it.copy(value = "")
        }
    }
}
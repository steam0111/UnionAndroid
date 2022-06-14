package com.itrocket.union.inventory.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.ParamValueDomain

class InventoryInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers
) {

    fun createInventory(accountingObjects: List<AccountingObjectDomain>): InventoryCreateDomain {
        // add request
        return InventoryCreateDomain(
            number = "БП-00001374",
            time = "12:40",
            date = "12.12.12",
            documentInfo = listOf(
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
                "Систмный интегратор",
            )
        )
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
        val locationIndex = params.indexOfFirst { it.type == ManualType.LOCATION }
        mutableParams[locationIndex] =
            mutableParams[locationIndex].copy(paramValue = ParamValueDomain("", location))
        return mutableParams
    }

    fun clearParam(list: List<ParamDomain>, param: ParamDomain): List<ParamDomain> {
        val mutableList = list.toMutableList()
        val currentIndex = mutableList.indexOfFirst { it.type == param.type }
        mutableList[currentIndex] = mutableList[currentIndex].copy(paramValue = null)
        return mutableList
    }

    fun clearParams(list: List<ParamDomain>): List<ParamDomain> {
        return list.map {
            it.copy(paramValue = null)
        }
    }
}
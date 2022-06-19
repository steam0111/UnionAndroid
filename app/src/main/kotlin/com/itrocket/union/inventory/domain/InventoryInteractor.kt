package com.itrocket.union.inventory.domain

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.location.domain.entity.LocationDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.withContext

class InventoryInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers,
) {

    suspend fun createInventory(
        accountingObjects: List<AccountingObjectDomain>,
        params: List<ParamDomain>
    ): InventoryCreateDomain = withContext(coreDispatchers.io) {
        val organizationId =
            requireNotNull(value = params.find { it.type == ManualType.ORGANIZATION },
                lazyMessage = {
                    "organizationId must not be null"
                }).id
        val molId =
            requireNotNull(value = params.find { it.type == ManualType.MOL }, lazyMessage = {
                "molId must not be null"
            }).id
        val locationIds =
            requireNotNull(value = params.find { it.type == ManualType.LOCATION } as? LocationParamDomain,
                lazyMessage = {
                    "molId must not be null"
                }).ids

        val id = repository.createInventory(
            InventoryCreateSyncEntity(
                organizationId = organizationId,
                employeeId = molId,
                accountingObjectsIds = accountingObjects.map { it.id },
                locationIds = locationIds
            )
        )
        repository.getInventoryById(id)
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

    fun changeLocation(
        params: List<ParamDomain>,
        location: LocationParamDomain
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val locationIndex = params.indexOfFirst { it.type == ManualType.LOCATION }
        mutableParams[locationIndex] = location
        return mutableParams
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

    fun isParamsValid(params: List<ParamDomain>): Boolean {
        return params.all { it.value.isNotBlank() }
    }
}
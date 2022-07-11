package com.itrocket.union.inventory.domain

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.toAccountingObjectIdSyncEntity
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.getFilterLocationIds
import com.itrocket.union.manual.getMolId
import com.itrocket.union.manual.getOrganizationId
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
            requireNotNull(value = params.getOrganizationId(),
                lazyMessage = {
                    "organizationId must not be null"
                })

        val molId = params.getMolId()
        val locationIds = params.getFilterLocationIds()

        val id = repository.createInventory(
            InventoryCreateSyncEntity(
                organizationId = organizationId,
                employeeId = molId,
                accountingObjectsIds = accountingObjects.map {
                    it.toAccountingObjectIdSyncEntity()
                },
                locationIds = locationIds,
                inventoryStatus = InventoryStatus.CREATED.name,
                updateDate = System.currentTimeMillis()
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
        mutableList[currentIndex] = mutableList[currentIndex].toInitialState()
        return mutableList
    }

    fun clearParams(list: List<ParamDomain>): List<ParamDomain> {
        return list.map {
            it.toInitialState()
        }
    }

    fun isParamsValid(params: List<ParamDomain>): Boolean {
        return !params.getOrganizationId().isNullOrEmpty()
    }
}
package com.itrocket.union.inventory.domain

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.toAccountingObjectIdSyncEntity
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.manual.getFilterInventoryBaseId
import com.itrocket.union.manual.getFilterLocationIds
import com.itrocket.union.manual.getFilterStructuralLastId
import com.itrocket.union.manual.getMolId
import kotlinx.coroutines.withContext

class InventoryInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor
) {
    suspend fun createInventory(
        accountingObjects: List<AccountingObjectDomain>,
        params: List<ParamDomain>
    ): InventoryCreateDomain = withContext(coreDispatchers.io) {
        val structuralId =
            requireNotNull(value = params.getFilterStructuralLastId(ManualType.STRUCTURAL),
                lazyMessage = {
                    "organizationId must not be null"
                })

        val molId = params.getMolId()
        val locationIds = params.getFilterLocationIds(ManualType.LOCATION_INVENTORY)
        val id = repository.createInventory(
            InventoryCreateSyncEntity(
                structuralId = structuralId,
                employeeId = molId,
                accountingObjectsIds = accountingObjects.map {
                    it.toAccountingObjectIdSyncEntity()
                },
                locationIds = locationIds,
                inventoryStatus = InventoryStatus.CREATED.name,
                userInserted = authMainInteractor.getLogin(),
                userUpdated = null,
                inventoryBaseId = params.getFilterInventoryBaseId()
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
        val locationIndex = params.indexOfFirst { it.type == ManualType.LOCATION_INVENTORY }
        mutableParams[locationIndex] = location
        return mutableParams
    }

    fun changeStructural(
        params: List<ParamDomain>,
        structural: StructuralParamDomain
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val structuralIndex = params.indexOfFirst { it.type == structural.manualType }
        mutableParams[structuralIndex] = structural.copy(filtered = false)
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
        return !params.getFilterStructuralLastId(ManualType.STRUCTURAL).isNullOrEmpty()
    }
}
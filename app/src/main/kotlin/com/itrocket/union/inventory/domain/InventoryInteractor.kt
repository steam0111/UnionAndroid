package com.itrocket.union.inventory.domain

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.toAccountingObjectIdSyncEntity
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.employeeDetail.domain.EmployeeDetailInteractor
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
import com.itrocket.union.manual.getMolInDepartmentId
import com.itrocket.union.structural.domain.dependencies.StructuralRepository
import kotlinx.coroutines.withContext

class InventoryInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor,
    private val structuralRepository: StructuralRepository
) {

    suspend fun isExistNonMarkingAccountingObjects(accountingObjects: List<AccountingObjectDomain>) : Boolean {
        return withContext(coreDispatchers.io){
            accountingObjects.find { !it.marked } != null
        }
    }

    suspend fun createInventory(
        accountingObjects: List<AccountingObjectDomain>,
        params: List<ParamDomain>,
        isAccountingObjectLoad: Boolean
    ): InventoryCreateDomain = withContext(coreDispatchers.io) {
        val structuralId = params.getFilterStructuralLastId(ManualType.STRUCTURAL)
        val molId = params.getMolInDepartmentId()
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
        repository.getInventoryById(id = id, isAccountingObjectLoad = isAccountingObjectLoad)
    }

    suspend fun changeParams(
        params: List<ParamDomain>,
        newParams: List<ParamDomain>
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        mutableParams.forEachIndexed { index, paramDomain ->
            val newParam = newParams.find { it.type == paramDomain.type }
            if (newParam != null) {
                mutableParams[index] = newParam
            }
            if (newParam is StructuralParamDomain) {
                changeBalanceUnit(mutableParams, newParam)
            }
        }

        return mutableParams
    }

    private suspend fun changeBalanceUnit(
        params: MutableList<ParamDomain>,
        structural: StructuralParamDomain
    ) {
        if (structural.type == ManualType.STRUCTURAL) {
            val index = params.indexOfFirst { it.type == ManualType.BALANCE_UNIT }
            if (index >= 0) {
                val oldBalanceUnit = params[index]
                val newBalanceUnitPath = structuralRepository.getBalanceUnitFullPath(
                    structural.id,
                    mutableListOf()
                )
                params[index] =
                    (oldBalanceUnit as StructuralParamDomain).copy(structurals = newBalanceUnitPath.orEmpty())
            }
        }
    }

    fun clearParam(list: List<ParamDomain>, param: ParamDomain): List<ParamDomain> {
        val mutableList = list.toMutableList()
        val currentIndex = mutableList.indexOfFirst { it.type == param.type }
        mutableList[currentIndex] = mutableList[currentIndex].toInitialState()

        if (param.type == ManualType.STRUCTURAL) {
            val balanceUnitIndex = mutableList.indexOfFirst { it.type == ManualType.BALANCE_UNIT }
            if (balanceUnitIndex >= 0) {
                mutableList[balanceUnitIndex] = mutableList[balanceUnitIndex].toInitialState()
            }
        }
        return mutableList
    }

    fun clearParams(list: List<ParamDomain>): List<ParamDomain> {
        return list.map {
            it.toInitialState()
        }
    }
}
package com.itrocket.union.inventory.domain

import com.example.union_sync_api.entity.InventoryCreateSyncEntity
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventory.domain.entity.InventoryNomenclatureDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.manual.getFilterInventoryBaseId
import com.itrocket.union.manual.getFilterLocationIds
import com.itrocket.union.manual.getFilterStructuralLastId
import com.itrocket.union.manual.getMolInDepartmentId
import com.itrocket.union.nomenclature.domain.NomenclatureInteractor
import com.itrocket.union.nomenclatureDetail.domain.NomenclatureDetailInteractor
import com.itrocket.union.reserves.domain.ReservesInteractor
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.structural.domain.dependencies.StructuralRepository
import java.util.UUID
import kotlinx.coroutines.withContext

class InventoryInteractor(
    private val repository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor,
    private val reservesInteractor: ReservesInteractor,
    private val nomenclatureDetailInteractor: NomenclatureDetailInteractor,
    private val structuralRepository: StructuralRepository
) {

    suspend fun getInventoryNomenclatures(params: List<ParamDomain>): List<InventoryNomenclatureDomain> {
        val reserves = reservesInteractor.getReserves(
            params = params,
            searchText = ""
        )

        val inventoryNomenclaturesMap = resolveReservesToInventoryNomenclatures(
            inventoryNomenclaturesMap = hashMapOf(),
            reserves = reserves,
            isAddNew = true
        )

        return inventoryNomenclaturesMap.values.toList()
    }

    suspend fun resolveReservesToInventoryNomenclatures(
        inventoryNomenclaturesMap: HashMap<String, InventoryNomenclatureDomain>,
        reserves: List<ReservesDomain>,
        isAddNew: Boolean
    ): HashMap<String, InventoryNomenclatureDomain> {
        return withContext(coreDispatchers.io) {
            reserves.forEach {
                val key = it.nomenclatureId + it.consignment + it.unitPrice + it.bookKeepingInvoice
                val existInventoryNomenclature = inventoryNomenclaturesMap[key]
                if (existInventoryNomenclature != null) {
                    val expectedCount = (existInventoryNomenclature.expectedCount ?: 0) + 1
                    inventoryNomenclaturesMap[key] =
                        existInventoryNomenclature.copy(expectedCount = expectedCount)
                } else if (isAddNew && it.nomenclatureId != null) {
                    val nomenclatureName =
                        nomenclatureDetailInteractor.getNomenclatureDetail(it.nomenclatureId).name
                    inventoryNomenclaturesMap[key] = InventoryNomenclatureDomain(
                        id = UUID.randomUUID().toString(),
                        nomenclatureId = it.nomenclatureId,
                        updateDate = System.currentTimeMillis(),
                        expectedCount = 1,
                        actualCount = 0,
                        consignment = it.consignment,
                        bookKeepingInvoice = it.bookKeepingInvoice,
                        price = it.unitPrice,
                        cancel = false,
                        nomenclatureName = nomenclatureName,
                        insertDate = null,
                        userInserted = null
                    )
                }
            }
            inventoryNomenclaturesMap
        }
    }

    suspend fun isExistNonMarkingAccountingObjects(accountingObjects: List<AccountingObjectDomain>): Boolean {
        return withContext(coreDispatchers.io) {
            accountingObjects.find { !it.marked } != null
        }
    }

    suspend fun createInventory(
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
package com.itrocket.union.selectParams.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.employeeDetail.domain.EmployeeDetailInteractor
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SelectParamsInteractor(
    private val selectParamsRepository: SelectParamsRepository,
    private val authMainInteractor: AuthMainInteractor,
    private val employeeDetailInteractor: EmployeeDetailInteractor,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getParamValues(
        params: List<ParamDomain>,
        allParams: List<ParamDomain>,
        type: ManualType,
        searchText: String
    ): Flow<List<ParamDomain>> =
        when (type) {
            ManualType.MOL_IN_STRUCTURAL -> getMolsInStructural(allParams, searchText)
            ManualType.MOL -> getMols(searchText)
            ManualType.EXPLOITING -> getExploiting(searchText)
            ManualType.STATUS -> getAccountingObjectStatuses(searchText)
            ManualType.ACTION_BASE -> getActionBases(searchText)
            ManualType.INVENTORY_BASE -> getInventoryBases(searchText)

            ManualType.PROVIDER -> getProviders(searchText)
            ManualType.PRODUCER -> getProducers(searchText)
            ManualType.EQUIPMENT_TYPE -> getEquipmentTypes(searchText)
            ManualType.NOMENCLATURE_GROUP -> getNomenclatureGroup(searchText)
            ManualType.RECEPTION_CATEGORY -> getReceptionCategory(searchText)
            ManualType.RECIPIENT -> getRecipient(searchText)
            else -> flow { }
        }

    private suspend fun getMolsInStructural(
        allParams: List<ParamDomain>,
        searchText: String
    ): Flow<List<ParamDomain>> {
        val structuralId = allParams.firstOrNull { it.type == ManualType.STRUCTURAL_TO }?.id
        return selectParamsRepository.getEmployeesByStructural(
            type = ManualType.MOL_IN_STRUCTURAL,
            textQuery = searchText,
            structuralId = structuralId
        )
    }


    fun changeParamValue(
        params: List<ParamDomain>,
        currentStep: Int,
        paramValue: ParamDomain
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val currentParamValue = mutableParams[currentStep - 1].id
        mutableParams[currentStep - 1] =
            mutableParams[currentStep - 1].copy(
                id = if (currentParamValue != paramValue.id) {
                    paramValue.id
                } else {
                    ""
                },
                value = if (currentParamValue != paramValue.id) {
                    paramValue.value
                } else {
                    ""
                }
            )
        return mutableParams
    }

    private suspend fun getMols(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getEmployees(type = ManualType.MOL, textQuery = searchText)
    }

    private suspend fun getExploiting(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getEmployees(
            type = ManualType.EXPLOITING,
            textQuery = searchText
        )
    }

    private suspend fun getRecipient(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getEmployees(
            type = ManualType.RECIPIENT,
            textQuery = searchText
        )
    }

    private suspend fun getAccountingObjectStatuses(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getStatuses(textQuery = searchText)
    }

    private suspend fun getEquipmentTypes(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getEquipmentTypes(textQuery = searchText)
    }

    private suspend fun getProviders(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getProviders(textQuery = searchText)
    }

    private suspend fun getProducers(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getProducers(textQuery = searchText)
    }

    private suspend fun getActionBases(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getActionBases(textQuery = searchText)
    }

    private suspend fun getInventoryBases(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getInventoryBases(textQuery = searchText)
    }

    private suspend fun getNomenclatureGroup(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getNomenclatureGroup(textQuery = searchText)
    }

    private suspend fun getReceptionCategory(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getReceptionCategory(textQuery = searchText)
    }

    suspend fun getInitialDocumentParams(params: List<ParamDomain>): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val config = authMainInteractor.getMyConfig()
        val employeeId = config.employeeId

        val employee = try {
            employeeDetailInteractor.getEmployeeDetail(requireNotNull(employeeId))
        } catch (t: Throwable) {
            null
        }

        employee?.let {
            val index = params.indexOfFirst {
                it.type == ManualType.MOL_IN_STRUCTURAL
            }
            if (index >= 0 && mutableParams[index].value.isEmpty()) {
                mutableParams[index] = mutableParams[index].copy(value = it.name, id = it.id)
            }
        }
        return mutableParams
    }

    companion object {
        const val MIN_CURRENT_STEP = 1
    }
}
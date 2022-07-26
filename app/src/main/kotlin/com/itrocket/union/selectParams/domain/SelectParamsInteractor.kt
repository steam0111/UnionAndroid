package com.itrocket.union.selectParams.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SelectParamsInteractor(
    private val selectParamsRepository: SelectParamsRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getParamValues(type: ManualType, searchText: String): Flow<List<ParamDomain>> =
        when (type) {
            ManualType.ORGANIZATION -> getOrganizations(searchText)
            ManualType.MOL -> getMols(searchText)
            ManualType.EXPLOITING -> getExploiting(searchText)
            ManualType.STATUS -> getAccountingObjectStatuses(searchText)
            ManualType.BRANCH -> getBranches(searchText)
            ManualType.ACTION_BASE -> getActionBases(searchText)

            ManualType.DEPARTMENT,
            ManualType.DEPARTMENT_FROM,
            ManualType.DEPARTMENT_TO -> getDepartments(searchText, type)

            ManualType.PROVIDER -> getProviders(searchText)
            ManualType.PRODUCER -> getProducers(searchText)
            ManualType.EQUIPMENT_TYPE -> getEquipmentTypes(searchText)
            ManualType.NOMENCLATURE_GROUP -> getNomenclatureGroup(searchText)
            ManualType.RECEPTION_CATEGORY -> getReceptionCategory(searchText)
            else -> flow { }
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

    private suspend fun getOrganizations(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getOrganizationList(textQuery = searchText)
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

    private suspend fun getDepartments(
        searchText: String,
        type: ManualType
    ): Flow<List<ParamDomain>> {
        return selectParamsRepository.getDepartments(textQuery = searchText, type)
    }

    private suspend fun getBranches(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getBranches(textQuery = searchText)
    }

    private suspend fun getActionBases(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getActionBases(textQuery = searchText)
    }

    private suspend fun getNomenclatureGroup(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getNomenclatureGroup(textQuery = searchText)
    }

    private suspend fun getReceptionCategory(searchText: String): Flow<List<ParamDomain>> {
        return selectParamsRepository.getReceptionCategory(textQuery = searchText)
    }

    companion object {
        const val MIN_CURRENT_STEP = 1
    }
}
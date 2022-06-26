package com.itrocket.union.selectParams.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SelectParamsInteractor(
    private val selectParamsRepository: SelectParamsRepository,
    private val coreDispatchers: CoreDispatchers
) {

    private var organizations: Flow<List<ParamDomain>>? = null
    private var mols: Flow<List<ParamDomain>>? = null
    private var exploitings: Flow<List<ParamDomain>>? = null
    private var statuses: Flow<List<ParamDomain>>? = null
    private var departments: Flow<List<ParamDomain>>? = null
    private var providers: Flow<List<ParamDomain>>? = null
    private var producers: Flow<List<ParamDomain>>? = null
    private var equipmentTypes: Flow<List<ParamDomain>>? = null
    private var nomenclaturesGroups: Flow<List<ParamDomain>>? = null

    suspend fun getParamValues(type: ManualType, searchText: String): Flow<List<ParamDomain>> =
        when (type) {
            ManualType.ORGANIZATION -> getOrganizations(searchText)
            ManualType.MOL -> getMols(searchText)
            ManualType.LOCATION -> selectParamsRepository.getParamValues(type, searchText)
            ManualType.EXPLOITING -> getExploiting(searchText)
            ManualType.STATUS -> getAccountingObjectStatuses(searchText)
            ManualType.DEPARTMENT -> getDepartments(searchText)
            ManualType.PROVIDER -> getProviders(searchText)
            ManualType.PRODUCER -> getProducers(searchText)
            ManualType.EQUIPMENT_TYPE -> getEquipmentTypes(searchText)
            ManualType.NOMENCLATURE_GROUP -> getNomenclatureGroup(searchText)
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
        if (organizations == null) {
            organizations = selectParamsRepository.getOrganizationList()
        }
        return (organizations ?: selectParamsRepository.getOrganizationList()).map {
            it.filter {
                it.value.contains(other = searchText, ignoreCase = true)
            }
        }
    }

    private suspend fun getMols(searchText: String): Flow<List<ParamDomain>> {
        if (mols == null) {
            mols = selectParamsRepository.getEmployees(ManualType.MOL)
        }
        return (mols ?: selectParamsRepository.getEmployees(ManualType.MOL)).map {
            it.filter {
                it.value.contains(other = searchText, ignoreCase = true)
            }
        }
    }

    private suspend fun getExploiting(searchText: String): Flow<List<ParamDomain>> {
        if (exploitings == null) {
            exploitings = selectParamsRepository.getEmployees(ManualType.EXPLOITING)
        }
        return (exploitings ?: selectParamsRepository.getEmployees(ManualType.EXPLOITING)).map {
            it.filter {
                it.value.contains(other = searchText, ignoreCase = true)
            }
        }
    }

    private suspend fun getAccountingObjectStatuses(searchText: String): Flow<List<ParamDomain>> {
        if (statuses == null) {
            statuses = selectParamsRepository.getStatuses()
        }

        return (statuses ?: selectParamsRepository.getStatuses()).map {
            it.filter { param ->
                param.value.contains(other = searchText, ignoreCase = true)
            }
        }
    }

    private suspend fun getEquipmentTypes(searchText: String): Flow<List<ParamDomain>> {
        if (equipmentTypes == null) {
            equipmentTypes = selectParamsRepository.getEquipmentTypes()
        }

        return (equipmentTypes ?: selectParamsRepository.getEquipmentTypes()).map {
            it.filter { param ->
                param.value.contains(other = searchText, ignoreCase = true)
            }
        }
    }

    private suspend fun getProviders(searchText: String): Flow<List<ParamDomain>> {
        if (providers == null) {
            providers = selectParamsRepository.getProviders()
        }

        return (providers ?: selectParamsRepository.getProviders()).map {
            it.filter { param ->
                param.value.contains(other = searchText, ignoreCase = true)
            }
        }
    }

    private suspend fun getProducers(searchText: String): Flow<List<ParamDomain>> {
        if (producers == null) {
            producers = selectParamsRepository.getProducers()
        }

        return (producers ?: selectParamsRepository.getProducers()).map {
            it.filter { param ->
                param.value.contains(other = searchText, ignoreCase = true)
            }
        }
    }

    private suspend fun getDepartments(searchText: String): Flow<List<ParamDomain>> {
        if (departments == null) {
            departments = selectParamsRepository.getDepartments()
        }

        return (departments ?: selectParamsRepository.getDepartments()).map {
            it.filter { param ->
                param.value.contains(other = searchText, ignoreCase = true)
            }
        }
    }

    private suspend fun getNomenclatureGroup(searchText: String): Flow<List<ParamDomain>> {
        if (nomenclaturesGroups == null) {
            nomenclaturesGroups = selectParamsRepository.getNomenclatureGroup()
        }

        return (nomenclaturesGroups ?: selectParamsRepository.getNomenclatureGroup()).map {
            it.filter { param ->
                param.value.contains(other = searchText, ignoreCase = true)
            }
        }
    }

    companion object {
        const val MIN_CURRENT_STEP = 1
    }
}
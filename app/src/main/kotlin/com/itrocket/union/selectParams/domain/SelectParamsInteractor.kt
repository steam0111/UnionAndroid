package com.itrocket.union.selectParams.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.ParamValueDomain
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.withContext

class SelectParamsInteractor(
    private val selectParamsRepository: SelectParamsRepository,
    private val coreDispatchers: CoreDispatchers
) {

    private var organizations: List<ParamValueDomain> = listOf()

    suspend fun getParamValues(type: ManualType, searchText: String) =
        withContext(coreDispatchers.io) {
            when (type) {
                ManualType.ORGANIZATION -> getOrganizations(searchText)
                ManualType.MOL -> selectParamsRepository.getParamValues(type, searchText)
                ManualType.LOCATION -> selectParamsRepository.getParamValues(type, searchText)
            }
        }

    fun changeParamValue(
        params: List<ParamDomain>,
        currentStep: Int,
        paramValue: ParamValueDomain
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val currentParamValue = mutableParams[currentStep - 1].paramValue
        mutableParams[currentStep - 1] =
            mutableParams[currentStep - 1].copy(
                paramValue = if (currentParamValue != paramValue) {
                    paramValue
                } else {
                    null
                }
            )
        return mutableParams
    }

    private suspend fun getOrganizations(searchText: String): List<ParamValueDomain> {
        if (organizations.isEmpty()) {
            organizations = selectParamsRepository.getOrganizationList()
        }
        return organizations.filter {
            it.value.lowercase()
                .contains(other = searchText.lowercase(), ignoreCase = true)
        }
    }

    companion object {
        const val MIN_CURRENT_STEP = 1
    }
}
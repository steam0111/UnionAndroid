package com.itrocket.union.selectParams.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.withContext

class SelectParamsInteractor(
    private val selectParamsRepository: SelectParamsRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getParamValues(type: ManualType, searchText: String) =
        withContext(coreDispatchers.io) {
            when (type) {
                ManualType.ORGANIZATION -> selectParamsRepository.getOrganizationList()
                    .filter { it.lowercase().contains(searchText.lowercase()) }
                ManualType.MOL -> selectParamsRepository.getParamValues(type, searchText)
                ManualType.LOCATION -> selectParamsRepository.getParamValues(type, searchText)
            }
        }

    fun changeParamValue(
        params: List<ParamDomain>,
        currentStep: Int,
        paramValue: String
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val currentParamValue = mutableParams[currentStep - 1].value
        mutableParams[currentStep - 1] =
            mutableParams[currentStep - 1].copy(
                value = if (currentParamValue != paramValue) {
                    paramValue
                } else {
                    ""
                }
            )
        return mutableParams
    }

    companion object {
        const val MIN_CURRENT_STEP = 1
    }
}
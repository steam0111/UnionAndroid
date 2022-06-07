package com.itrocket.union.selectParams.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.withContext

class SelectParamsInteractor(
    private val selectParamsRepository: SelectParamsRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getParamValues(param: String) = withContext(coreDispatchers.io) {
        selectParamsRepository.getParamValues(param)
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
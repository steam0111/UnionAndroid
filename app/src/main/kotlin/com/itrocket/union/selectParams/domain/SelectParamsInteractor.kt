package com.itrocket.union.selectParams.domain

import android.util.Log
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.ParamValueDomain
import com.itrocket.union.selectParams.domain.dependencies.SelectParamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SelectParamsInteractor(
    private val selectParamsRepository: SelectParamsRepository,
    private val coreDispatchers: CoreDispatchers
) {

    private var organizations: Flow<List<ParamValueDomain>>? = null

    suspend fun getParamValues(type: ManualType, searchText: String): Flow<List<ParamValueDomain>> =
        when (type) {
            ManualType.ORGANIZATION -> getOrganizations(searchText)
            ManualType.MOL -> selectParamsRepository.getParamValues(type, searchText)
            ManualType.LOCATION -> selectParamsRepository.getParamValues(type, searchText)
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

    private suspend fun getOrganizations(searchText: String): Flow<List<ParamValueDomain>> {
        if (organizations == null) {
            organizations = selectParamsRepository.getOrganizationList()
        }
        return organizations?.map {
                it.filter {
                    it.value.contains(other = searchText, ignoreCase = true)
                }
            } ?: flow { }
    }

    companion object {
        const val MIN_CURRENT_STEP = 1
    }
}
package com.itrocket.union.selectParams.domain.dependencies

import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.flow.Flow

interface SelectParamsRepository {


    suspend fun getEmployees(type: ManualType, textQuery: String?): Flow<List<ParamDomain>>

    suspend fun getStatuses(textQuery: String?): Flow<List<ParamDomain>>

    suspend fun getEquipmentTypes(textQuery: String?): Flow<List<ParamDomain>>

    suspend fun getProviders(textQuery: String?): Flow<List<ParamDomain>>

    suspend fun getProducers(textQuery: String?): Flow<List<ParamDomain>>

    suspend fun getNomenclatureGroup(textQuery: String?): Flow<List<ParamDomain>>

    suspend fun getReceptionCategory(textQuery: String?): Flow<List<ParamDomain>>

    suspend fun getActionBases(textQuery: String?): Flow<List<ParamDomain>>

    suspend fun getInventoryBases(textQuery: String?): Flow<List<ParamDomain>>

    suspend fun getEmployeesByStructural(
        structuralId: String?,
        type: ManualType,
        textQuery: String?
    ): Flow<List<ParamDomain>>

    suspend fun getDocumentsCodes(number: String, documentType: String?): Flow<List<ParamDomain>>

    suspend fun getInventoriesCodes(number: String): Flow<List<ParamDomain>>

}
package com.itrocket.union.documentCreate.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.domain.dependencies.DocumentCreateRepository
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.toUpdateSyncEntity
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.withContext

class DocumentCreateInteractor(
    private val repository: DocumentCreateRepository,
    private val documentRepository: DocumentRepository,
    private val coreDispatchers: CoreDispatchers
) {

    fun changeParams(params: List<ParamDomain>, newParams: List<ParamDomain>): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        mutableParams.forEachIndexed { index, paramDomain ->
            val newParam = newParams.find { it.type == paramDomain.type }
            if (newParam != null) {
                mutableParams[index] = newParam
            }
        }
        return mutableParams
    }

    fun changeLocation(params: List<ParamDomain>, location: String): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val locationIndex = params.indexOfFirst { it.type == ManualType.LOCATION }
        mutableParams[locationIndex] =
            mutableParams[locationIndex].copy(value = location)
        return mutableParams
    }

    fun clearParam(list: List<ParamDomain>, param: ParamDomain): List<ParamDomain> {
        val mutableList = list.toMutableList()
        val currentIndex = mutableList.indexOfFirst { it.type == param.type }
        mutableList[currentIndex] = mutableList[currentIndex].copy(value = "")
        return mutableList
    }

    fun clearParams(list: List<ParamDomain>): List<ParamDomain> {
        return list.map {
            it.copy(value = "")
        }
    }

    fun isParamsFilled(params: List<ParamDomain>): Boolean = !params.any { it.value.isBlank() }

    fun addAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObjectDomain: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjects.toMutableList()
        mutableList.add(accountingObjectDomain)
        return mutableList
    }

    suspend fun saveDocument(
        document: DocumentDomain,
        accountingObjects: List<AccountingObjectDomain>,
        params: List<ParamDomain>
    ) {
        withContext(coreDispatchers.io) {
            documentRepository.updateDocument(
                document.copy(accountingObjects = accountingObjects, params = params)
                    .toUpdateSyncEntity()
            )
        }
    }
}
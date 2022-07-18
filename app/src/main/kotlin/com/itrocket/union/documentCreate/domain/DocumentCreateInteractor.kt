package com.itrocket.union.documentCreate.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.DocumentStatus
import com.itrocket.union.documents.domain.entity.DocumentTypeDomain
import com.itrocket.union.documents.domain.entity.toCreateSyncEntity
import com.itrocket.union.documents.domain.entity.toUpdateSyncEntity
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import kotlinx.coroutines.withContext

class DocumentCreateInteractor(
    private val documentRepository: DocumentRepository,
    private val accountingObjectRepository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getDocumentById(
        id: String
    ): DocumentDomain {
        return withContext(coreDispatchers.io) {
            documentRepository.getDocumentById(id)
        }
    }

    suspend fun createOrUpdateDocument(
        document: DocumentDomain,
        accountingObjects: List<AccountingObjectDomain>,
        reserves: List<ReservesDomain>,
        params: List<ParamDomain>,
        status: DocumentStatus
    ): String {
        return withContext(coreDispatchers.io) {
            if (!document.isDocumentCreated) {
                documentRepository.createDocument(
                    document.copy(
                        accountingObjects = accountingObjects,
                        params = params,
                        documentStatus = status,
                        documentStatusId = status.name,
                        reserves = reserves
                    ).toCreateSyncEntity()
                )
            } else {
                documentRepository.updateDocument(
                    document.copy(
                        accountingObjects = accountingObjects,
                        params = params,
                        documentStatus = status,
                        documentStatusId = status.name,
                        reserves = reserves
                    ).toUpdateSyncEntity()
                )
                document.number.orEmpty()
            }
        }
    }

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

    fun changeLocation(
        params: List<ParamDomain>,
        location: LocationParamDomain
    ): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val locationIndex = params.indexOfFirst { it.type == location.manualType }
        mutableParams[locationIndex] = location.copy(filtered = false)
        return mutableParams
    }

    fun clearParam(list: List<ParamDomain>, param: ParamDomain): List<ParamDomain> {
        val mutableList = list.toMutableList()
        val currentIndex = mutableList.indexOfFirst { it.type == param.type }
        mutableList[currentIndex] = mutableList[currentIndex].toInitialState()
        return mutableList
    }

    fun clearParams(list: List<ParamDomain>): List<ParamDomain> {
        return list.map {
            it.toInitialState()
        }
    }

    fun getAccountingObjectIds(accountingObjects: List<AccountingObjectDomain>): List<String> =
        accountingObjects.map { it.id }

    fun getReservesIds(reserves: List<ReservesDomain>): List<String> =
        reserves.map { it.id }

    fun isParamsValid(
        params: List<ParamDomain>,
        documentTypeDomain: DocumentTypeDomain,
    ): Boolean {
        val validateParams = params.filter {
            documentTypeDomain.manualTypes.contains(it.type)
        }
        return validateParams.all { it.value.isNotBlank() }
    }


    fun addAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObjectDomain: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjects.toMutableList()
        mutableList.add(accountingObjectDomain)
        return mutableList
    }

    fun updateReserveCount(
        reserves: List<ReservesDomain>,
        id: String,
        count: Long
    ): List<ReservesDomain> {
        val mutableList = reserves.toMutableList()
        val index = mutableList.indexOfFirst { it.id == id }
        val reserve = mutableList[index]
        mutableList[index] = reserve.copy(itemsCount = count)
        return mutableList
    }

    fun addReserve(
        reserves: List<ReservesDomain>,
        reserve: ReservesDomain
    ): List<ReservesDomain> {
        val mutableList = reserves.toMutableList()
        mutableList.add(reserve)
        return mutableList
    }

    suspend fun handleNewAccountingObjectRfids(
        accountingObjects: List<AccountingObjectDomain>,
        handledAccountingObjectRfids: List<String>
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectRfids = mutableListOf<String>()

            handledAccountingObjectRfids.forEach { rfid ->
                val index = accountingObjects.indexOfFirst { it.rfidValue == rfid }
                if (index == NO_POSITION) {
                    newAccountingObjectRfids.add(rfid)
                }
            }
            val newAccountingObjects =
                accountingObjectRepository.getAccountingObjectsByRfids(newAccountingObjectRfids)
            newAccountingObjects + accountingObjects
        }
    }

    suspend fun handleNewAccountingObjectBarcode(
        accountingObjects: List<AccountingObjectDomain>,
        barcode: String,
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectBarcode = mutableListOf<AccountingObjectDomain>()

            val index = accountingObjects.indexOfFirst { it.barcodeValue == barcode }
            if (index == NO_POSITION) {
                val barcodeAccountingObject =
                    accountingObjectRepository.getAccountingObjectsByBarcode(barcode)
                barcodeAccountingObject?.let {
                    newAccountingObjectBarcode.add(it)
                }
            }
            newAccountingObjectBarcode + accountingObjects
        }
    }

    suspend fun getFilterParams(params: List<ParamDomain>) = withContext(coreDispatchers.io) {
        params.filter { it.isFilter }
    }

    companion object {
        private const val NO_POSITION = -1
    }
}
package com.itrocket.union.documentCreate.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.domain.dependencies.DocumentCreateRepository
import com.itrocket.union.documents.domain.dependencies.DocumentRepository
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.documents.domain.entity.toUpdateSyncEntity
import com.itrocket.union.inventoryCreate.domain.InventoryCreateInteractor
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectsDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.withContext

class DocumentCreateInteractor(
    private val repository: DocumentCreateRepository,
    private val documentRepository: DocumentRepository,
    private val accountingObjectRepository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers
) {

    suspend fun getDocumentById(
        id: String
    ): DocumentDomain {
        return withContext(coreDispatchers.io) {
            documentRepository.getDocumentById(id.toLong())
        }
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
        val locationIndex = params.indexOfFirst { it.type == ManualType.LOCATION }
        mutableParams[locationIndex] = location
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

    fun getAccountingObjectIds(accountingObjects: List<AccountingObjectDomain>): List<String> =
        accountingObjects.map { it.id }

    fun isParamsValid(params: List<ParamDomain>): Boolean =
        params.all { it.value.isNotBlank() || it.type == ManualType.LOCATION }

    fun addAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObjectDomain: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjects.toMutableList()
        mutableList.add(accountingObjectDomain)
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

    companion object {
        private const val NO_POSITION = -1
    }
}
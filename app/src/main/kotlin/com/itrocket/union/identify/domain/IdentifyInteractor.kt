package com.itrocket.union.identify.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import com.itrocket.union.documents.domain.entity.DocumentDomain
import com.itrocket.union.identify.domain.dependencies.IdentifyRepository
import com.itrocket.union.identify.domain.entity.IdentifyDomain
import com.itrocket.union.manual.ParamDomain
import kotlinx.coroutines.withContext

class IdentifyInteractor(
    private val identifyRepository: IdentifyRepository,
    private val accountingObjectRepository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers
) {
    suspend fun getIdentify() = withContext(coreDispatchers.io) {
        identifyRepository.getIdentify()
    }

    suspend fun getDocumentById(
        id: String
    ): IdentifyDomain {
        return withContext(coreDispatchers.io) {
            identifyRepository.getDocumentById(id)
        }
    }

    fun clearParam(list: List<ParamDomain>, param: ParamDomain): List<ParamDomain> {
        val mutableList = list.toMutableList()
        val currentIndex = mutableList.indexOfFirst { it.type == param.type }
        mutableList[currentIndex] = mutableList[currentIndex].copy(id = "", value = "")
        return mutableList
    }

    fun clearParams(list: List<ParamDomain>): List<ParamDomain> {
        return list.map {
            it.copy(id = "", value = "")
        }
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

    fun addAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObjectDomain: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjects.toMutableList()
        mutableList.add(accountingObjectDomain)
        return mutableList
    }

    companion object {
        private const val NO_POSITION = -1
    }
}
package com.itrocket.union.identify.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.documentCreate.domain.DocumentCreateInteractor
import kotlinx.coroutines.withContext

class IdentifyInteractor(
    private val accountingObjectRepository: AccountingObjectRepository,
    private val coreDispatchers: CoreDispatchers,
) {
    fun addAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObject: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjects.toMutableList()
        mutableList.add(accountingObject)
        return mutableList
    }

    suspend fun handleNewAccountingObjectRfids(
        accountingObjects: List<AccountingObjectDomain>,
        handledAccountingObjectRfid: String
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectRfids = mutableListOf<String>()

            val index =
                accountingObjects.indexOfFirst { it.rfidValue == handledAccountingObjectRfid }
            if (index == NO_POSITION) {
                newAccountingObjectRfids.add(handledAccountingObjectRfid)
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
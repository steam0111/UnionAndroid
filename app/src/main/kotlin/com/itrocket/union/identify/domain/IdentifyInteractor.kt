package com.itrocket.union.identify.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
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
        handledAccountingObjectRfids: List<String>
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectRfids = mutableListOf<String>()
            val existAccountingObjectRfids = hashMapOf<String, String>()

            accountingObjects.forEach {
                if (it.rfidValue != null) {
                    existAccountingObjectRfids[it.rfidValue] = it.id
                }
            }

            handledAccountingObjectRfids.forEach { rfid ->
                val isExist = existAccountingObjectRfids[rfid] != null
                if (!isExist) {
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
        isSerialNumber: Boolean
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectBarcode = mutableListOf<AccountingObjectDomain>()

            val index = accountingObjects.indexOfFirst {
                if (isSerialNumber) {
                    it.factoryNumber == barcode
                } else {
                    it.barcodeValue == barcode
                }
            }
            if (index == NO_POSITION) {
                val barcodeAccountingObject =
                    if (isSerialNumber) {
                        accountingObjectRepository.getAccountingObjectsByBarcode(
                            barcode = null,
                            serialNumber = barcode
                        )
                    } else {
                        accountingObjectRepository.getAccountingObjectsByBarcode(
                            barcode = barcode,
                            serialNumber = null
                        )
                    }
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
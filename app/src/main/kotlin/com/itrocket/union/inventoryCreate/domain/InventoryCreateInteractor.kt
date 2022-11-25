package com.itrocket.union.inventoryCreate.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.domain.entity.AccountingObjectCounter
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.domain.entity.ScannedAccountingObjects
import com.itrocket.union.inventoryCreate.domain.entity.toUpdateSyncEntity
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue
import kotlinx.coroutines.withContext
import timber.log.Timber

class InventoryCreateInteractor(
    private val accountingObjectRepository: AccountingObjectRepository,
    private val inventoryRepository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor
) {

    @OptIn(ExperimentalTime::class)
    suspend fun getInventoryById(id: String, isAccountingObjectLoad: Boolean) =
        withContext(coreDispatchers.io) {
            val result = measureTimedValue {
                Timber.d("inventoryRepository.getInventoryById($id)")

                val inventory = inventoryRepository.getInventoryById(
                    id = id,
                    isAccountingObjectLoad = isAccountingObjectLoad
                )
                val sortedAccountingObjects = inventory.accountingObjects.sortedBy {
                    it.inventoryStatus.priority
                }
                inventory.copy(accountingObjects = sortedAccountingObjects)
            }
            Timber.d(
                "Запрос на инвентарную ведомость выполнился за " +
                        "result ${result.duration.inWholeMilliseconds}"
            )

            return@withContext result.value
        }

    suspend fun saveInventoryDocument(
        inventoryCreate: InventoryCreateDomain,
        accountingObjects: List<AccountingObjectDomain>
    ) = withContext(coreDispatchers.io) {
        inventoryRepository.updateInventory(
            inventoryCreate.copy(
                accountingObjects = accountingObjects,
                userUpdated = authMainInteractor.getLogin()
            ).toUpdateSyncEntity()
        )
    }

    suspend fun removeAccountingObject(
        accountingObjectId: String,
        accountingObjects: List<AccountingObjectDomain>
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val mutableAccountingObjects = accountingObjects.toMutableList()

            val existAccountingObjectIndex =
                mutableAccountingObjects.indexOfFirst { it.id == accountingObjectId }

            if (existAccountingObjectIndex >= 0) {
                mutableAccountingObjects.removeAt(existAccountingObjectIndex)
            }

            mutableAccountingObjects
        }
    }

    suspend fun changeAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObject: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val mutableAccountingObjects = accountingObjects.toMutableList()

            val existAccountingObjectIndex =
                mutableAccountingObjects.indexOfFirst { it.id == accountingObject.id }

            if (existAccountingObjectIndex >= 0) {
                mutableAccountingObjects[existAccountingObjectIndex] =
                    mutableAccountingObjects[existAccountingObjectIndex].copy(
                        rfidValue = accountingObject.rfidValue,
                        barcodeValue = accountingObject.barcodeValue,
                        factoryNumber = accountingObject.factoryNumber
                    )
            }

            mutableAccountingObjects
        }
    }

    suspend fun handleNewAccountingObjectRfids(
        accountingObjects: List<AccountingObjectDomain>,
        handledAccountingObjectIds: List<String>,
        inventoryStatus: InventoryStatus,
        isAddNew: Boolean,
    ): ScannedAccountingObjects {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectRfids = mutableListOf<String>()
            val mutableAccountingObjects = accountingObjects.toMutableList()

            handledAccountingObjectIds.forEach { handledAccountingObjectId ->
                val accountingObjectIndex = mutableAccountingObjects.indexOfFirst {
                    it.rfidValue == handledAccountingObjectId
                }
                val isStatusNew = if (accountingObjectIndex != NO_INDEX) {
                    mutableAccountingObjects[accountingObjectIndex].inventoryStatus == InventoryAccountingObjectStatus.NEW
                } else {
                    false
                }
                when {
                    accountingObjectIndex != NO_INDEX && !isStatusNew && inventoryStatus != InventoryStatus.COMPLETED -> changeStatusByIndex(
                        mutableAccountingObjects,
                        accountingObjectIndex
                    )
                    accountingObjectIndex == NO_INDEX && inventoryStatus != InventoryStatus.COMPLETED && isAddNew -> newAccountingObjectRfids.add(
                        handledAccountingObjectId
                    )
                }
            }

            val handledAccountingObjects =
                getHandlesAccountingObjectByRfid(newAccountingObjectRfids)
            hasWrittenOffAccountingObjects(
                newAccountingObjects = handledAccountingObjects,
                existAccountingObjects = mutableAccountingObjects
            )
        }
    }

    suspend fun handleNewAccountingObjectBarcode(
        accountingObjects: List<AccountingObjectDomain>,
        barcode: String,
        inventoryStatus: InventoryStatus,
        isAddNew: Boolean,
        isSerialNumber: Boolean
    ): ScannedAccountingObjects {
        return withContext(coreDispatchers.io) {
            val barcodeAccountingObjects = mutableListOf<AccountingObjectDomain>()
            val mutableAccountingObjects = accountingObjects.toMutableList()

            val accountingObjectIndex = mutableAccountingObjects.indexOfFirst {
                if (isSerialNumber) {
                    it.factoryNumber == barcode
                } else {
                    it.barcodeValue == barcode
                }
            }
            val isStatusNew = if (accountingObjectIndex != NO_INDEX) {
                mutableAccountingObjects[accountingObjectIndex].inventoryStatus == InventoryAccountingObjectStatus.NEW
            } else {
                false
            }

            when {
                accountingObjectIndex != NO_INDEX && !isStatusNew && inventoryStatus != InventoryStatus.COMPLETED -> changeStatusByIndex(
                    mutableAccountingObjects,
                    accountingObjectIndex
                )
                accountingObjectIndex == NO_INDEX && inventoryStatus != InventoryStatus.COMPLETED && isAddNew -> {
                    val accountingObjectDomain = getHandleAccountingObjectByBarcode(
                        barcode = barcode,
                        isSerialNumber = isSerialNumber
                    )
                    if (accountingObjectDomain != null) {
                        barcodeAccountingObjects.add(accountingObjectDomain)
                    }
                }
            }
            hasWrittenOffAccountingObjects(
                newAccountingObjects = barcodeAccountingObjects,
                existAccountingObjects = mutableAccountingObjects
            )
        }
    }

    private fun hasWrittenOffAccountingObjects(
        newAccountingObjects: List<AccountingObjectDomain>,
        existAccountingObjects: List<AccountingObjectDomain>
    ): ScannedAccountingObjects {
        val filterWriteOffAccountingObjects =
            newAccountingObjects.filter { !it.isWrittenOff }
        return ScannedAccountingObjects(
            accountingObjects = filterWriteOffAccountingObjects + existAccountingObjects,
            hasWrittenOffAccountingObjects = newAccountingObjects.size > filterWriteOffAccountingObjects.size
        )
    }

    fun disableBalanceUnit(params: List<ParamDomain>): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val index = params.indexOfFirst { it.type == ManualType.BALANCE_UNIT }
        if (index >= 0) {
            mutableParams[index] =
                (mutableParams[index] as StructuralParamDomain).copy(clickable = false)
        }
        return mutableParams
    }

    suspend fun changeStatus(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObjectId: String
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val mutableAccountingObjects = accountingObjects.toMutableList()
            val index = mutableAccountingObjects.indexOfFirst { it.id == accountingObjectId }
            if (index >= 0) {
                val findAccountingObject = mutableAccountingObjects[index]
                val newStatus =
                    if (findAccountingObject.inventoryStatus == InventoryAccountingObjectStatus.FOUND) {
                        InventoryAccountingObjectStatus.NOT_FOUND
                    } else {
                        InventoryAccountingObjectStatus.FOUND
                    }
                val manualInput = if (newStatus == InventoryAccountingObjectStatus.FOUND) {
                    true
                } else {
                    null
                }
                mutableAccountingObjects[index] =
                    findAccountingObject.copy(
                        inventoryStatus = newStatus,
                        manualInput = manualInput
                    )
            }
            mutableAccountingObjects
        }
    }

    suspend fun searchAccountingObjects(
        searchText: String,
        accountingObjects: List<AccountingObjectDomain>,
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val resultList = accountingObjects.filter {
                val title = it.title.replace(" ", "")
                val searchTitle = searchText.replace(" ", "")
                val inventoryNumber = it.inventoryNumber.orEmpty().lowercase().replace(" ", "")

                title.contains(other = searchTitle, ignoreCase = true)
                        || inventoryNumber.contains(other = searchText, ignoreCase = true)
                        || searchText.isEmpty()
            }
            resultList
        }
    }

    fun getAccountingObjectCount(
        accountingObjects: List<AccountingObjectDomain>,
    ): AccountingObjectCounter {
        return AccountingObjectCounter(
            total = accountingObjects.filter {
                it.inventoryStatus == InventoryAccountingObjectStatus.FOUND ||
                        it.inventoryStatus == InventoryAccountingObjectStatus.NOT_FOUND
            }.size,
            found = accountingObjects.filter {
                it.inventoryStatus == InventoryAccountingObjectStatus.FOUND
            }.size,
            notFound = accountingObjects.filter {
                it.inventoryStatus == InventoryAccountingObjectStatus.NOT_FOUND
            }.size,
            new = accountingObjects.filter {
                it.inventoryStatus == InventoryAccountingObjectStatus.NEW
            }.size
        )
    }

    private suspend fun getHandlesAccountingObjectByRfid(rfids: List<String>): List<AccountingObjectDomain> {
        return accountingObjectRepository.getAccountingObjectsByRfids(rfids)
            .map {
                it.copy(inventoryStatus = InventoryAccountingObjectStatus.NEW)
            }
    }

    private suspend fun getHandleAccountingObjectByBarcode(
        barcode: String,
        isSerialNumber: Boolean
    ): AccountingObjectDomain? {
        return if (isSerialNumber) {
            accountingObjectRepository.getAccountingObjectsByBarcode(
                barcode = null,
                serialNumber = barcode
            )
        } else {
            accountingObjectRepository.getAccountingObjectsByBarcode(
                barcode = barcode,
                serialNumber = null
            )
        }?.copy(inventoryStatus = InventoryAccountingObjectStatus.NEW)

    }

    private fun changeStatusByIndex(
        accountingObjects: MutableList<AccountingObjectDomain>,
        index: Int
    ) {
        val accountingObject = accountingObjects[index]
        accountingObjects.removeAt(index)
        accountingObjects.add(
            0,
            accountingObject.copy(
                inventoryStatus = InventoryAccountingObjectStatus.FOUND,
                manualInput = false
            )
        )
    }

    companion object {
        private const val NO_INDEX = -1
    }
}
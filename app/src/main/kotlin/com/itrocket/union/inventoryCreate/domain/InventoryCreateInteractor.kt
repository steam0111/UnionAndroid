package com.itrocket.union.inventoryCreate.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventory.domain.dependencies.InventoryRepository
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectsDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.inventoryCreate.domain.entity.toUpdateSyncEntity
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.switcher.domain.entity.SwitcherDomain
import kotlinx.coroutines.withContext

class InventoryCreateInteractor(
    private val accountingObjectRepository: AccountingObjectRepository,
    private val inventoryRepository: InventoryRepository,
    private val coreDispatchers: CoreDispatchers,
    private val authMainInteractor: AuthMainInteractor
) {

    suspend fun getInventoryById(id: String) =
        withContext(coreDispatchers.io) {
            inventoryRepository.getInventoryById(id)
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

    suspend fun handleNewAccountingObjectRfids(
        accountingObjects: List<AccountingObjectDomain>,
        handledAccountingObjectId: String,
        inventoryStatus: InventoryStatus,
        isAddNew: Boolean,
        existNewAccountingObjects: List<AccountingObjectDomain>
    ): InventoryAccountingObjectsDomain {
        return withContext(coreDispatchers.io) {
            val newAccountingObjectRfids = mutableListOf<String>()
            val mutableAccountingObjects = accountingObjects.toMutableList()

            val accountingObjectIndex = mutableAccountingObjects.indexOfFirst {
                it.rfidValue == handledAccountingObjectId
            }
            val isRfidNewExist =
                existNewAccountingObjects.indexOfFirst { it.rfidValue == handledAccountingObjectId } != NO_INDEX
            val isNewExist = if (accountingObjectIndex != NO_INDEX) {
                mutableAccountingObjects[accountingObjectIndex].inventoryStatus == InventoryAccountingObjectStatus.NEW
            } else {
                false
            }
            when {
                accountingObjectIndex != NO_INDEX && !isNewExist && inventoryStatus != InventoryStatus.COMPLETED -> changeStatusByIndex(
                    mutableAccountingObjects,
                    accountingObjectIndex
                )
                accountingObjectIndex == NO_INDEX && inventoryStatus != InventoryStatus.COMPLETED && isAddNew && !isRfidNewExist -> newAccountingObjectRfids.add(
                    handledAccountingObjectId
                )
            }

            val handledAccountingObjects =
                getHandlesAccountingObjectByRfid(newAccountingObjectRfids)
            InventoryAccountingObjectsDomain(
                newAccountingObjects = handledAccountingObjects,
                createdAccountingObjects = mutableAccountingObjects
            )
        }
    }

    suspend fun handleNewAccountingObjectBarcode(
        accountingObjects: List<AccountingObjectDomain>,
        barcode: String,
        inventoryStatus: InventoryStatus,
        isAddNew: Boolean,
        existNewAccountingObjects: List<AccountingObjectDomain>
    ): InventoryAccountingObjectsDomain {
        return withContext(coreDispatchers.io) {
            val barcodeAccountingObjects = mutableListOf<AccountingObjectDomain>()
            val mutableAccountingObjects = accountingObjects.toMutableList()

            val accountingObjectIndex = mutableAccountingObjects.indexOfFirst {
                it.barcodeValue == barcode
            }
            val isRfidNewExist =
                existNewAccountingObjects.indexOfFirst { it.barcodeValue == barcode } != NO_INDEX
            val isNewExist = if (accountingObjectIndex != NO_INDEX) {
                mutableAccountingObjects[accountingObjectIndex].inventoryStatus == InventoryAccountingObjectStatus.NEW
            } else {
                false
            }

            when {
                accountingObjectIndex != NO_INDEX && !isNewExist && inventoryStatus != InventoryStatus.COMPLETED -> changeStatusByIndex(
                    mutableAccountingObjects,
                    accountingObjectIndex
                )
                accountingObjectIndex == NO_INDEX && inventoryStatus != InventoryStatus.COMPLETED && isAddNew && !isRfidNewExist -> {
                    val accountingObjectDomain = getHandleAccountingObjectByBarcode(barcode)
                    if (accountingObjectDomain != null) {
                        barcodeAccountingObjects.add(accountingObjectDomain)
                    }
                }
            }

            InventoryAccountingObjectsDomain(
                newAccountingObjects = barcodeAccountingObjects,
                createdAccountingObjects = mutableAccountingObjects
            )
        }
    }

    fun disableBalanceUnit(params: List<ParamDomain>): List<ParamDomain> {
        val mutableParams = params.toMutableList()
        val index = params.indexOfFirst { it.type == ManualType.BALANCE_UNIT }
        if (index >= 0) {
            mutableParams[index] = mutableParams[index].copy(isClickable = false)
        }
        return mutableParams
    }

    fun dropAccountingObjects(accountingObjects: List<AccountingObjectDomain>): List<AccountingObjectDomain> {
        return accountingObjects.map { it.copy() }
    }

    fun changeAccountingObjectInventoryStatus(
        accountingObjects: List<AccountingObjectDomain>,
        switcherDomain: SwitcherDomain
    ): List<AccountingObjectDomain> {
        val mutableList = accountingObjects.toMutableList()
        val newStatus = switcherDomain.currentValue
        val index = accountingObjects.indexOfFirst { it.id == switcherDomain.entityId }
        mutableList[index] =
            mutableList[index].copy(inventoryStatus = newStatus as InventoryAccountingObjectStatus)
        return mutableList
    }

    fun isNewAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        newAccountingObject: AccountingObjectDomain
    ): Boolean {
        return !accountingObjects.contains(newAccountingObject) || newAccountingObject.inventoryStatus == InventoryAccountingObjectStatus.NEW
    }

    private suspend fun getHandlesAccountingObjectByRfid(rfids: List<String>): List<AccountingObjectDomain> {
        return accountingObjectRepository.getAccountingObjectsByRfids(rfids)
            .map {
                it.copy(inventoryStatus = InventoryAccountingObjectStatus.NEW)
            }
    }

    private suspend fun getHandleAccountingObjectByBarcode(barcode: String): AccountingObjectDomain? {
        return accountingObjectRepository.getAccountingObjectsByBarcode(barcode)
            ?.copy(inventoryStatus = InventoryAccountingObjectStatus.NEW)

    }

    private fun changeStatusByIndex(
        accountingObjects: MutableList<AccountingObjectDomain>,
        index: Int
    ) {
        val accountingObject = accountingObjects[index]
        accountingObjects[index] =
            accountingObject.copy(inventoryStatus = InventoryAccountingObjectStatus.FOUND)
    }

    suspend fun searchAccountingObjects(
        searchText: String,
        accountingObjects: List<AccountingObjectDomain>,
        newAccountingObject: List<AccountingObjectDomain>
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val allAccountingObjects = newAccountingObject + accountingObjects
            val resultList = allAccountingObjects.filter {
                val title = it.title.lowercase().replace(" ", "")
                val searchTitle = searchText.lowercase().replace(" ", "")
                val inventoryNumber = it.inventoryNumber.orEmpty().lowercase().replace(" ", "")

                title.contains(searchTitle) || inventoryNumber.contains(searchText) || searchText.isEmpty()
            }
            resultList
        }
    }


    companion object {
        private const val NO_INDEX = -1
    }
}
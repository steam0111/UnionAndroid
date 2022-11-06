package com.itrocket.union.comment.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectsDomain
import kotlinx.coroutines.withContext

class CommentInteractor(private val coreDispatchers: CoreDispatchers) {

    suspend fun changeAccountingObjectComment(
        accountingObjectId: String,
        comment: String,
        listAccountingObject: List<AccountingObjectDomain>,
        newAccountingObjects: List<AccountingObjectDomain>
    ): InventoryAccountingObjectsDomain {
        return withContext(coreDispatchers.io) {
            val mutableAccountingObjects = listAccountingObject.toMutableList()
            var listNewAccountingObjects = newAccountingObjects

            val accountingObjectIndex =
                mutableAccountingObjects.indexOfFirst { it.id == accountingObjectId }

            if (accountingObjectIndex != NO_POSITION) {
                mutableAccountingObjects[accountingObjectIndex] =
                    mutableAccountingObjects[accountingObjectIndex].copy(comment = comment)
            } else {
                listNewAccountingObjects = changeNewAccountingObjectComment(
                    accountingObjectId = accountingObjectId,
                    comment = comment,
                    newAccountingObjects = newAccountingObjects
                )
            }

            InventoryAccountingObjectsDomain(
                createdAccountingObjects = mutableAccountingObjects,
                newAccountingObjects = listNewAccountingObjects
            )
        }
    }

    private suspend fun changeNewAccountingObjectComment(
        accountingObjectId: String,
        comment: String,
        newAccountingObjects: List<AccountingObjectDomain>
    ): List<AccountingObjectDomain> {
        val mutableNewAccountingObjects = newAccountingObjects.toMutableList()
        val newAccountingObjectIndex =
            mutableNewAccountingObjects.indexOfFirst { it.id == accountingObjectId }
        if (newAccountingObjectIndex != NO_POSITION) {
            mutableNewAccountingObjects[newAccountingObjectIndex] =
                mutableNewAccountingObjects[newAccountingObjectIndex].copy(comment = comment)
        }
        return mutableNewAccountingObjects
    }

    companion object {
        const val NO_POSITION = -1
    }
}
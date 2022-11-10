package com.itrocket.union.comment.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import kotlinx.coroutines.withContext

class CommentInteractor(private val coreDispatchers: CoreDispatchers) {

    suspend fun changeAccountingObjectComment(
        accountingObjectId: String,
        comment: String,
        listAccountingObject: List<AccountingObjectDomain>,
    ): List<AccountingObjectDomain> {
        return withContext(coreDispatchers.io) {
            val mutableAccountingObjects = listAccountingObject.toMutableList()

            val accountingObjectIndex =
                mutableAccountingObjects.indexOfFirst { it.id == accountingObjectId }

            if (accountingObjectIndex != NO_POSITION) {
                mutableAccountingObjects[accountingObjectIndex] =
                    mutableAccountingObjects[accountingObjectIndex].copy(comment = comment)
            }

            mutableAccountingObjects
        }
    }

    companion object {
        const val NO_POSITION = -1
    }
}
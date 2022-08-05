package com.itrocket.union.selectActionWithValuesBottomMenu.domain

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain

class SelectActionWithValuesBottomMenuInteractor {
    fun removeAccountingObject(
        accountingObjects: List<AccountingObjectDomain>,
        accountingObject: AccountingObjectDomain
    ): List<AccountingObjectDomain> {
        val newList = accountingObjects.toMutableList()
        newList.remove(accountingObject)
        return newList
    }
}
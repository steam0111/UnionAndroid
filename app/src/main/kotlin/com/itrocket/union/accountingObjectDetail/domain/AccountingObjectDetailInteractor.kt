package com.itrocket.union.accountingObjectDetail.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository

class AccountingObjectDetailInteractor(
    private val repository: AccountingObjectDetailRepository,
    private val coreDispatchers: CoreDispatchers
) {

}
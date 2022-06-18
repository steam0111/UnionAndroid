package com.itrocket.union.accountingObjects.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.accountingObjects.data.mapper.map
import kotlinx.coroutines.withContext

class AccountingObjectRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AccountingObjectSyncApi
) : AccountingObjectRepository {

    override suspend fun getAccountingObjects() = withContext(coreDispatchers.io) {
        syncApi.getAccountingObjects().map()
    }

    // Надо добавить фильтрацию по параметрам.
    // У параметра есть тип, который показывает, значение какого справочника там лежит.
    // Если необходимо, для локации можно создать наследника от ParamDomain, который будет содержать дерево.
    override suspend fun getAccountingObjectsByParams(params: List<ParamDomain>): List<AccountingObjectDomain> {
        return listOf(
            AccountingObjectDomain(
                id = "5",
                isBarcode = true,
                title = "Ширикоформатный жидкокристалический монитор Samsung",
                status = ObjectStatus.UNDER_REPAIR,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf()
            )
        )
    }

}
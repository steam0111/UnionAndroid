package com.itrocket.union.accountingObjects.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.dependencies.AccountingObjectRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AccountingObjectRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val syncApi: AccountingObjectSyncApi
) : AccountingObjectRepository {

    override suspend fun getAccountingObjects(): Flow<List<AccountingObjectDomain>> =
        withContext(coreDispatchers.io) {
            syncApi.getAccountingObjects().map { it.map() }
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
                status = ObjectStatus("available", ObjectStatusType.UNDER_REPAIR),
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        R.string.accounting_object_detail_title,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.accounting_object_detail_title,
                        "таылватвлыавыалвыоалвыа"
                    ),
                ),
                listAdditionallyInfo = listOf()
            )
        )
    }

}
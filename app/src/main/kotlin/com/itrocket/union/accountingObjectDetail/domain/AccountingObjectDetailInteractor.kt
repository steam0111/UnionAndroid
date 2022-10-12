package com.itrocket.union.accountingObjectDetail.domain

import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoType
import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import com.itrocket.union.unionPermissions.domain.entity.UnionPermission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AccountingObjectDetailInteractor(
    private val repository: AccountingObjectDetailRepository,
    private val coreDispatchers: CoreDispatchers,
    private val unionPermissionsInteractor: UnionPermissionsInteractor
) {

    suspend fun getAccountingObject(id: String): AccountingObjectDomain =
        withContext(coreDispatchers.io) {
            repository.getAccountingObject(id)
        }

    suspend fun getAccountingObjectByParams(
        rfid: String? = null,
        barcode: String? = null,
        factoryNumber: String? = null
    ): AccountingObjectDomain = withContext(coreDispatchers.io) {
        repository.getAccountingObjectByParams(
            rfid = rfid,
            barcode = barcode,
            factoryNumber = factoryNumber
        )
    }

    suspend fun getAccountingObjectFlow(id: String): Flow<AccountingObjectDomain> =
        repository.getAccountingObjectFlow(id).map {
            val mainInfo = checkAdditionalListPermissions(it.listMainInfo)
            it.copy(listMainInfo = mainInfo)
        }

    private suspend fun checkAdditionalListPermissions(listInfo: List<ObjectInfoDomain>): List<ObjectInfoDomain> {
        var mainInfo = listInfo
        val isCanShowSimpleFields =
            unionPermissionsInteractor.canRead(UnionPermission.ACCOUNTING_OBJECT_SIMPLE_ADDITIONAL_FIELD)
        val isCanShowVocabularyFields =
            unionPermissionsInteractor.canRead(UnionPermission.ACCOUNTING_OBJECT_VOCABULARY_ADDITIONAL_FIELD)
        if (!isCanShowSimpleFields) {
            mainInfo =
                mainInfo.filter { it.filedType != ObjectInfoType.SIMPLE_ADDITIONAL_FIELD }
        }
        if (!isCanShowVocabularyFields) {
            mainInfo =
                mainInfo.filter { it.filedType != ObjectInfoType.VOCABULARY_ADDITIONAL_FIELD }
        }
        return mainInfo
    }

    suspend fun updateScanningData(accountingObjectDomain: AccountingObjectDomain) =
        withContext(coreDispatchers.io) {
            repository.updateScanningData(accountingObjectDomain)
        }
}
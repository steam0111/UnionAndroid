package com.itrocket.union.accountingObjectDetail.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.image.domain.ImageDomain
import kotlinx.coroutines.flow.Flow

interface AccountingObjectDetailRepository {
    suspend fun getAccountingObject(id: String): AccountingObjectDomain

    suspend fun getAccountingObjectByParams(
        rfid: String? = null,
        barcode: String? = null,
        factoryNumber: String? = null
    ): AccountingObjectDomain

    suspend fun getAccountingObjectFlow(id: String): Flow<AccountingObjectDomain>

    suspend fun writeOffAccountingObject(accountingObject: AccountingObjectDomain)

    suspend fun updateScanningData(accountingObject: AccountingObjectDomain)

    suspend fun updateLabelType(accountingObject: AccountingObjectDomain, labelTypeId: String)

    suspend fun getAccountingObjectImages(accountingObjectId: String) : List<ImageDomain>
}
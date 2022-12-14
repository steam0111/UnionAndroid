package com.itrocket.union.accountingObjectDetail.domain.dependencies

import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
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

    suspend fun saveImage(
        imageDomain: ImageDomain,
        accountingObjectId: String,
        userInserted: String?
    )

    suspend fun updateScanningData(accountingObject: AccountingObjectDomain)

    suspend fun updateLabelType(accountingObject: AccountingObjectDomain, labelTypeId: String)

    suspend fun getAccountingObjectImagesByUpdateDate(updateDate: Long): List<ImageDomain>

    suspend fun getAccountingObjectImagesFlow(accountingObjectId: String): Flow<List<ImageDomain>>

    suspend fun deleteAccountingObjectImage(imageId: String)

    suspend fun updateIsMainImage(newMainImageId: String?, oldMainImageId: String?)
}
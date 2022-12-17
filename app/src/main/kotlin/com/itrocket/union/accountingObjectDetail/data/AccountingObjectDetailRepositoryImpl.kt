package com.itrocket.union.accountingObjectDetail.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.data.AccountingObjectUnionImageSyncApi
import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.entity.AccountingObjectImageMainUpdate
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.data.mapper.toAccountingObjectDetailDomain
import com.itrocket.union.accountingObjectDetail.data.mapper.toAccountingObjectLabelType
import com.itrocket.union.accountingObjectDetail.data.mapper.toAccountingObjectScanningData
import com.itrocket.union.accountingObjectDetail.data.mapper.toAccountingObjectWriteOff
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.image.data.toDomain
import com.itrocket.union.image.data.toSyncEntity
import com.itrocket.union.image.domain.ImageDomain
import com.itrocket.union.image.domain.dependencies.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AccountingObjectDetailRepositoryImpl(
    private val syncApi: AccountingObjectSyncApi,
    private val enumsSyncApi: EnumsSyncApi,
    private val coreDispatchers: CoreDispatchers,
    private val imageRepository: ImageRepository,
    private val accountingObjectUnionImageSyncApi: AccountingObjectUnionImageSyncApi,
) : AccountingObjectDetailRepository {

    override suspend fun getAccountingObject(id: String): AccountingObjectDomain {
        return syncApi.getAccountingObjectDetailById(id).toAccountingObjectDetailDomain()
    }

    override suspend fun getAccountingObjectByParams(
        rfid: String?,
        barcode: String?,
        factoryNumber: String?
    ): AccountingObjectDomain {
        return syncApi.getAccountingObjectDetailByParams(
            rfid = rfid,
            barcode = barcode,
            factoryNumber = factoryNumber
        ).toAccountingObjectDetailDomain()
    }

    override suspend fun getAccountingObjectFlow(id: String): Flow<AccountingObjectDomain> {
        return syncApi.getAccountingObjectDetailByIdFlow(id)
            .map { it.toAccountingObjectDetailDomain() }
    }

    override suspend fun writeOffAccountingObject(accountingObject: AccountingObjectDomain) {
        withContext(coreDispatchers.io) {
            syncApi.writeOffAccountingObject(accountingObject.toAccountingObjectWriteOff())
        }
    }

    override suspend fun updateScanningData(accountingObject: AccountingObjectDomain) {
        return syncApi.updateAccountingObjectScanningData(accountingObject.toAccountingObjectScanningData())
    }

    override suspend fun updateLabelType(
        accountingObject: AccountingObjectDomain,
        labelTypeId: String
    ) {
        return syncApi.updateAccountingObjectLabelType(
            accountingObject.toAccountingObjectLabelType(
                labelTypeId
            )
        )
    }

    override suspend fun updateAccountingObjectMarked(accountingObjectId: String, rfid: String) {
        syncApi.updateAccountingObjectMarked(accountingObjectId, rfid)
    }

    override suspend fun saveImage(
        imageDomain: ImageDomain,
        accountingObjectId: String,
        userInserted: String?
    ) {
        withContext(coreDispatchers.io) {
            accountingObjectUnionImageSyncApi.saveAccountingObjectImage(
                imageDomain.toSyncEntity(
                    accountingObjectId = accountingObjectId,
                    userInserted = userInserted
                )
            )
        }
    }

    override suspend fun getAccountingObjectImagesByUpdateDate(updateDate: Long): List<ImageDomain> {
        return withContext(coreDispatchers.io) {
            accountingObjectUnionImageSyncApi.getAccountingObjectImages(updateDate = updateDate)
                .map {
                    val imageFile = imageRepository.getImageByName(it.unionImageId)
                    it.toDomain(imageFile)
                }
        }
    }

    override suspend fun getAccountingObjectImagesFlow(accountingObjectId: String): Flow<List<ImageDomain>> {
        return withContext(coreDispatchers.io) {
            val imagesFlow =
                accountingObjectUnionImageSyncApi.getAccountingObjectImagesByIdFlow(
                    accountingObjectId
                )
            imagesFlow.map { imageList ->
                imageList.map { image ->
                    val imageFile = imageRepository.getImageByName(image.unionImageId)
                    image.toDomain(imageFile)
                }
            }
        }
    }

    override suspend fun deleteAccountingObjectImage(imageId: String) {
        return withContext(coreDispatchers.io) {
            accountingObjectUnionImageSyncApi.deleteAccountingObjectImage(imageId)
        }
    }

    override suspend fun updateIsMainImage(newMainImageId: String?, oldMainImageId: String?) {
        return withContext(coreDispatchers.io) {
            val update = buildList {
                if (newMainImageId != null) {
                    add(
                        AccountingObjectImageMainUpdate(
                            isMainImage = true,
                            imageId = newMainImageId
                        )
                    )
                }
                if (oldMainImageId != null) {
                    add(
                        AccountingObjectImageMainUpdate(
                            isMainImage = false,
                            imageId = oldMainImageId
                        )
                    )
                }
            }
            accountingObjectUnionImageSyncApi.changeMainImage(update)
        }
    }

    override suspend fun getAccountingObjectImages(accountingObjectId: String): List<ImageDomain> {
        return listOf()
    }
}
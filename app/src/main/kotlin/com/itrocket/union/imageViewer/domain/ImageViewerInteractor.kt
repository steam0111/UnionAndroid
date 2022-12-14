package com.itrocket.union.imageViewer.domain

import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.image.domain.ImageDomain
import com.itrocket.union.image.domain.dependencies.ImageRepository
import com.itrocket.union.imageViewer.domain.entity.ImagesDelete
import kotlinx.coroutines.withContext

class ImageViewerInteractor(
    private val accountingObjectDetailRepository: AccountingObjectDetailRepository,
    private val authMainInteractor: AuthMainInteractor,
    private val coreDispatchers: CoreDispatchers
) {
    fun getImagePage(image: ImageDomain, images: List<ImageDomain>): Int = images.indexOf(image)

    suspend fun changeMainImage(images: List<ImageDomain>, page: Int) {
        return withContext(coreDispatchers.io) {
            val oldMainImage = images.find { it.isMainImage }
            val newMainImage = if (images[page] == oldMainImage) {
                null
            } else {
                images[page]
            }
            accountingObjectDetailRepository.updateIsMainImage(
                newMainImageId = newMainImage?.imageId,
                oldMainImageId = oldMainImage?.imageId
            )
        }
    }

    suspend fun deleteImage(images: List<ImageDomain>, page: Int) {
        return withContext(coreDispatchers.io) {
            val deleteImageId = images[page].imageId
            accountingObjectDetailRepository.deleteAccountingObjectImage(deleteImageId)
        }
    }

    suspend fun saveImage(image: ImageDomain, accountingObjectId: String) {
        withContext(coreDispatchers.io) {
            accountingObjectDetailRepository.saveImage(
                imageDomain = image,
                accountingObjectId = accountingObjectId,
                userInserted = authMainInteractor.getLogin()
            )
        }
    }

    suspend fun getImagesFlow(entityId: String) = withContext(coreDispatchers.io) {
        accountingObjectDetailRepository.getAccountingObjectImagesFlow(entityId)
    }
}
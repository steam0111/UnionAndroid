package com.itrocket.union.imageViewer.domain

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.image.ImageDomain
import com.itrocket.union.imageViewer.domain.dependencies.ImageViewerRepository
import com.itrocket.union.imageViewer.domain.entity.ImagesDelete
import kotlinx.coroutines.withContext

class ImageViewerInteractor(
    private val repository: ImageViewerRepository,
    private val coreDispatchers: CoreDispatchers
) {

    fun getMockImages() = repository.getMockImages()

    suspend fun getImagesFromImagesDomain(images: List<ImageDomain>): List<ImageDomain> {
        return repository.getImagesFromImagesDomain(images)
    }

    fun getImagePage(image: ImageDomain, images: List<ImageDomain>): Int = images.indexOf(image)

    suspend fun changeMainImage(images: List<ImageDomain>, page: Int): List<ImageDomain> {
        return withContext(coreDispatchers.io) {
            val mutableImages = images.toMutableList()
            val currentImage = images[page]
            if (currentImage.isMainImage) {
                mutableImages[page] = currentImage.copy(isMainImage = false)
            } else {
                resolveMainImage(mutableImages = mutableImages, page = page)
            }
            mutableImages
        }
    }

    suspend fun deleteImage(images: List<ImageDomain>, page: Int): ImagesDelete {
        return withContext(coreDispatchers.io) {
            val mutableImages = images.toMutableList()
            mutableImages.removeAt(page)
            val newPage = if (page == images.lastIndex) {
                page - 1
            } else {
                page
            }
            ImagesDelete(images = mutableImages, newPage = newPage)
        }
    }

    private suspend fun resolveMainImage(
        mutableImages: MutableList<ImageDomain>,
        page: Int
    ): MutableList<ImageDomain> {
        return withContext(coreDispatchers.io) {
            val mainIndex = mutableImages.indexOfFirst { it.isMainImage }
            if (mainIndex >= 0) {
                mutableImages[mainIndex] = mutableImages[mainIndex].copy(isMainImage = false)
            }
            mutableImages[page] = mutableImages[page].copy(isMainImage = true)
            mutableImages
        }
    }
}
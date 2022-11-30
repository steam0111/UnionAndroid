package com.itrocket.union.imageViewer.domain.dependencies

import com.itrocket.union.image.ImageDomain

interface ImageViewerRepository {

    suspend fun getImagesFromImagesDomain(images: List<ImageDomain>): List<ImageDomain>

    fun getMockImages(): List<ImageDomain> //TODO: Удалить, когда доделают бэк
}
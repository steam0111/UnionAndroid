package com.itrocket.union.image.domain

import android.net.Uri
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.image.domain.dependencies.ImageRepository

class ImageInteractor(
    private val repository: ImageRepository,
    private val coreDispatchers: CoreDispatchers,
) {

    suspend fun getTmpFileUri() = repository.getTmpFileUri()

    suspend fun saveImageFromContentUri(uri: Uri) = repository.saveImage(uri)

    suspend fun getImagesFromImagesDomain(images: List<ImageDomain>) =
        repository.getImagesFromImagesDomain(images)

    fun addImageDomain(images: List<ImageDomain>, image: ImageDomain) = images + image
}
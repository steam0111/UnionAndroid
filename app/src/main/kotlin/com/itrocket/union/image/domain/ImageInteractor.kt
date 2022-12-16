package com.itrocket.union.image.domain

import android.net.Uri
import com.itrocket.union.image.domain.dependencies.ImageRepository

class ImageInteractor(
    private val repository: ImageRepository,
) {

    suspend fun getTmpFileUri() = repository.getTmpFileUri()

    suspend fun saveImageFromContentUri(uri: Uri) = repository.saveImage(uri)

    suspend fun getImagesFromImagesDomain(images: List<ImageDomain>) =
        repository.getImagesFromImagesDomain(images)

}
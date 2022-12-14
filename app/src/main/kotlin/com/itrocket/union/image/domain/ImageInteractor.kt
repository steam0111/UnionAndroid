package com.itrocket.union.image.domain

import android.net.Uri
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.accountingObjectDetail.domain.dependencies.AccountingObjectDetailRepository
import com.itrocket.union.authMain.domain.AuthMainInteractor
import com.itrocket.union.image.domain.dependencies.ImageRepository
import kotlinx.coroutines.withContext

class ImageInteractor(
    private val repository: ImageRepository,
) {

    suspend fun getTmpFileUri() = repository.getTmpFileUri()

    suspend fun saveImageFromContentUri(uri: Uri) = repository.saveImage(uri)

    suspend fun getImagesFromImagesDomain(images: List<ImageDomain>) =
        repository.getImagesFromImagesDomain(images)

}
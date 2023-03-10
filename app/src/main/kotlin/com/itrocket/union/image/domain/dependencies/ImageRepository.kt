package com.itrocket.union.image.domain.dependencies

import android.net.Uri
import com.itrocket.union.image.domain.ImageDomain
import java.io.File

interface ImageRepository {

    suspend fun getImagesFromImagesDomain(images: List<ImageDomain>): List<ImageDomain>

    suspend fun getTmpFileUri(): Uri

    suspend fun saveImage(imageUri: Uri): ImageDomain

    suspend fun getImageByName(imageName: String): File
}
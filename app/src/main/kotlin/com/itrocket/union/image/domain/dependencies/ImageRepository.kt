package com.itrocket.union.image.domain.dependencies

import android.net.Uri
import com.itrocket.union.image.domain.ImageDomain
import java.io.File

interface ImageRepository {

    suspend fun getImageFromName(imageName: String): File

    suspend fun getTmpFileUri(): Uri

    suspend fun saveImage(imageUri: Uri): ImageDomain
}
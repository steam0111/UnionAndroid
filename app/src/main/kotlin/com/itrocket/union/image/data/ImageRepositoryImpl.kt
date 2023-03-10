package com.itrocket.union.image.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.BuildConfig
import com.itrocket.union.image.domain.ImageDomain
import com.itrocket.union.image.domain.dependencies.ImageRepository
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File

class ImageRepositoryImpl(
    private val applicationContext: Context,
    private val coreDispatchers: CoreDispatchers
) : ImageRepository {

    override suspend fun getImageByName(imageName: String): File {
        return File(getImagesDirectory().absolutePath + "/$imageName")
    }

    override suspend fun getImagesFromImagesDomain(images: List<ImageDomain>): List<ImageDomain> {
        return withContext(coreDispatchers.io) {
            images.map {
                it.copy(imageFile = getImageByName(it.imageId))
            }
        }
    }

    override suspend fun getTmpFileUri(): Uri {
        return withContext(coreDispatchers.io) {
            val tmpFile =
                File.createTempFile(
                    TMP_FILE_NAME,
                    TMP_FILE_MIME_TYPE,
                    applicationContext.cacheDir
                ).apply {
                    createNewFile()
                    deleteOnExit()
                }

            FileProvider.getUriForFile(
                applicationContext,
                getFileAuthority(),
                tmpFile
            )
        }
    }

    override suspend fun saveImage(imageUri: Uri): ImageDomain {
        return withContext(coreDispatchers.io) {
            val directory = getImagesDirectory()


            val fileName = IMAGE_NAME_PREFIX + System.currentTimeMillis().toString()
            val newImageFile = File(directory.absolutePath, fileName)

            newImageFile.createNewFile()

            val parcelFileDescriptor =
                applicationContext.contentResolver.openFileDescriptor(imageUri, "r")

            val imageBitmap =
                BitmapFactory.decodeFileDescriptor(parcelFileDescriptor?.fileDescriptor, null, null)
            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()

            newImageFile.outputStream().use {
                it.write(bytes)
            }

            ImageDomain.create(
                isMainImage = false,
                imageFile = newImageFile,
                imageId = fileName
            )
        }
    }

    private fun getImagesDirectory(): File {
        val directory = File(applicationContext.filesDir, IMAGES_DIRECTORY)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directory
    }


    private fun getFileAuthority() = "${BuildConfig.APPLICATION_ID}.provider"

    companion object {
        private const val IMAGE_NAME_PREFIX = "ao_image_"
        private const val TMP_FILE_NAME = "tmp_image_file"
        private const val TMP_FILE_MIME_TYPE = ".png"
        private const val IMAGES_DIRECTORY = "images"
    }
}

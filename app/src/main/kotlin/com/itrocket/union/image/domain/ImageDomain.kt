package com.itrocket.union.image.domain

import android.os.Parcelable
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
class ImageDomain(
    val imagePath: String,
    val isMainImage: Boolean,
) : Parcelable {
    @IgnoredOnParcel
    var imageFile: File? = null
        private set

    fun copy(
        imagePath: String = this.imagePath,
        isMainImage: Boolean = this.isMainImage,
        imageFile: File? = this.imageFile
    ): ImageDomain {
        return create(imagePath = imagePath, isMainImage = isMainImage, imageFile = imageFile)
    }

    companion object {
        fun create(
            imagePath: String,
            isMainImage: Boolean,
            imageFile: File?
        ): ImageDomain {
            return ImageDomain(
                imagePath = imagePath,
                isMainImage = isMainImage,
            ).apply {
                this.imageFile = imageFile
            }
        }
    }
}
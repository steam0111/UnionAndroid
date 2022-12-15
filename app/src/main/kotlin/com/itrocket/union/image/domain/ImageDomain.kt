package com.itrocket.union.image.domain

import android.os.Parcelable
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
class ImageDomain(
    val imageId: String,
    val isMainImage: Boolean,
) : Parcelable {
    @IgnoredOnParcel
    var imageFile: File? = null
        private set

    fun copy(
        isMainImage: Boolean = this.isMainImage,
        imageFile: File? = this.imageFile,
        imageId: String = this.imageId
    ): ImageDomain {
        return create(
            isMainImage = isMainImage,
            imageFile = imageFile,
            imageId = imageId
        )
    }

    companion object {
        fun create(
            isMainImage: Boolean,
            imageId: String,
            imageFile: File?
        ): ImageDomain {
            return ImageDomain(
                isMainImage = isMainImage,
                imageId = imageId
            ).apply {
                this.imageFile = imageFile
            }
        }
    }
}

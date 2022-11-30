package com.itrocket.union.image

import android.os.Parcelable
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ImageDomain(
    val imagePath: String,
    val isMainImage: Boolean,
    @IgnoredOnParcel val imageBitmap: @RawValue ImageBitmap? = null
) : Parcelable {

}
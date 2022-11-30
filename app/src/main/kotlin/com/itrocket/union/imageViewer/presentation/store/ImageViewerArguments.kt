package com.itrocket.union.imageViewer.presentation.store

import android.os.Parcelable
import com.itrocket.union.image.ImageDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageViewerArguments(val images: List<ImageDomain>, val currentImage: ImageDomain) :
    Parcelable
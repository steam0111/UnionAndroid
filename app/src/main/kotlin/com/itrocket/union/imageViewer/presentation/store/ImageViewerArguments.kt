package com.itrocket.union.imageViewer.presentation.store

import android.os.Parcelable
import com.itrocket.union.image.domain.ImageDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageViewerArguments(
    val images: List<ImageDomain>,
    val currentImage: ImageDomain,
    val accountingObjectId: String
) :
    Parcelable
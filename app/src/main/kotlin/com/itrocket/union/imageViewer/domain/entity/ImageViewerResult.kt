package com.itrocket.union.imageViewer.domain.entity

import android.os.Parcelable
import com.itrocket.union.image.ImageDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageViewerResult(val images: List<ImageDomain>) : Parcelable
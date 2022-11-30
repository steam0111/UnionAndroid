package com.itrocket.union.imageViewer.data.mapper

import com.itrocket.union.imageViewer.domain.entity.ImageViewerDomain

fun List<Any>.map(): List<ImageViewerDomain> = map {
    ImageViewerDomain()
}
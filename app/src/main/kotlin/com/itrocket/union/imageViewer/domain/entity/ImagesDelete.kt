package com.itrocket.union.imageViewer.domain.entity

import com.itrocket.union.image.domain.ImageDomain

data class ImagesDelete(val images: List<ImageDomain>, val newPage: Int)
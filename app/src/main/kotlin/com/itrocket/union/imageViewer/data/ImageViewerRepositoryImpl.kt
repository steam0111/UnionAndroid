package com.itrocket.union.imageViewer.data

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.image.ImageDomain
import com.itrocket.union.imageViewer.domain.dependencies.ImageViewerRepository
import kotlinx.coroutines.withContext

class ImageViewerRepositoryImpl(
    private val coreDispatchers: CoreDispatchers,
    private val applicationContext: Context
) :
    ImageViewerRepository {

    override suspend fun getImagesFromImagesDomain(images: List<ImageDomain>): List<ImageDomain> {
        return withContext(coreDispatchers.io) {
            val resource = applicationContext.resources
            val mutableImages = images.toMutableList()
            mutableImages.map {
                it.copy(imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock1))
            }
        }
    }

    override fun getMockImages(): List<ImageDomain> {
        val resource = applicationContext.resources
        return listOf(
            ImageDomain(
                imagePath = "",
                imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock1),
                isMainImage = false
            ),
            ImageDomain(
                imagePath = "",
                imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock2),
                isMainImage = false
            ),
            ImageDomain(
                imagePath = "",
                imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock3),
                isMainImage = false
            ),
            ImageDomain(
                imagePath = "",
                imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock1),
                isMainImage = false
            ),
            ImageDomain(
                imagePath = "",
                imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock2),
                isMainImage = true
            ),
            ImageDomain(
                imagePath = "",
                imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock3),
                isMainImage = false
            ),
            ImageDomain(
                imagePath = "",
                imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock1),
                isMainImage = false
            ),
            ImageDomain(
                imagePath = "",
                imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock2),
                isMainImage = false
            ),
            ImageDomain(
                imagePath = "",
                imageBitmap = ImageBitmap.imageResource(resource, R.drawable.mock3),
                isMainImage = false
            ),
        )
    }
}
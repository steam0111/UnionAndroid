package com.itrocket.union.ui.image

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.image.ImageDomain
import com.itrocket.union.ui.black
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.psb4
import com.itrocket.union.ui.psb5
import com.itrocket.union.ui.white

private const val CELLS_COUNT = 4
private const val GRID_IMAGE_SIZE = 76

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridImages(
    images: List<ImageDomain>,
    onImageClickListener: (ImageDomain) -> Unit,
    canAddImage: Boolean = false,
    onAddImageClickListener: () -> Unit = {},
) {

    LazyVerticalGrid(
        cells = GridCells.Fixed(CELLS_COUNT),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(white)
            .padding(16.dp)
    ) {
        if (canAddImage) {
            item {
                AddImageComponent(onAddImageClickListener = onAddImageClickListener)
            }
        }
        items(images) {
            CellImage(imageDomain = it, onImageClickListener = onImageClickListener)
        }
    }
}

@Composable
fun CellImage(imageDomain: ImageDomain, onImageClickListener: (ImageDomain) -> Unit) {
    Box(
        modifier = Modifier
            .size(GRID_IMAGE_SIZE.dp)
            .clickable(
                onClick = { onImageClickListener(imageDomain) },
                indication = rememberRipple(),
                interactionSource = MutableInteractionSource()
            )
            .background(black),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            bitmap = imageDomain.image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        if (imageDomain.isMainImage) {
            Image(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun AddImageComponent(onAddImageClickListener: () -> Unit) {
    Box(modifier = Modifier.padding(10.dp)) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(psb5, RoundedCornerShape(8.dp))
                .border(1.dp, psb4, RoundedCornerShape(8.dp))
                .clickable(
                    onClick = onAddImageClickListener,
                    indication = rememberRipple(),
                    interactionSource = MutableInteractionSource()
                )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = null,
                colorFilter = ColorFilter.tint(psb1),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
@Preview
private fun GridImagesPreview() {
    GridImages(
        canAddImage = true,
        images = listOf(
            ImageDomain(ImageBitmap.imageResource(id = R.drawable.mock1), isMainImage = false),
            ImageDomain(ImageBitmap.imageResource(id = R.drawable.mock2), isMainImage = false),
            ImageDomain(
                ImageBitmap.imageResource(id = R.drawable.mock3),
                isMainImage = false
            ),
            ImageDomain(
                ImageBitmap.imageResource(id = R.drawable.mock1),
                isMainImage = false
            ),
            ImageDomain(ImageBitmap.imageResource(id = R.drawable.mock2), isMainImage = true),
            ImageDomain(ImageBitmap.imageResource(id = R.drawable.mock3), isMainImage = false),
            ImageDomain(ImageBitmap.imageResource(id = R.drawable.mock1), isMainImage = false),
            ImageDomain(ImageBitmap.imageResource(id = R.drawable.mock2), isMainImage = false),
            ImageDomain(ImageBitmap.imageResource(id = R.drawable.mock3), isMainImage = false),
        ),
        onAddImageClickListener = {},
        onImageClickListener = {}
    )
}

@Composable
@Preview
private fun AddImageComponentPreview() {
    AddImageComponent {

    }
}
package com.itrocket.union.imageViewer.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.image.domain.ImageDomain
import com.itrocket.union.imageViewer.presentation.store.ImageViewerStore
import com.itrocket.union.ui.*
import com.itrocket.utils.clickableUnbounded
import kotlinx.coroutines.flow.collect
import java.io.File

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageViewerScreen(
    state: ImageViewerStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onMainClickListener: () -> Unit,
    onDeleteClickListener: () -> Unit,
    onAddPhotoClickListener: () -> Unit,
    onImageSwipe: (Int) -> Unit
) {
    AppTheme {
        val pagerState = rememberPagerState(state.page)

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp, bottom = appInsets.bottomInset.dp),
            topBar = {
                BaseToolbar(
                    backgroundColor = white,
                    startImageId = R.drawable.ic_cross,
                    onStartImageClickListener = onBackClickListener,
                    textColor = psb1,
                    title = stringResource(
                        id = R.string.common_of,
                        state.page + 1,
                        state.images.size
                    )
                )
            },
            bottomBar = {
                BottomBar(
                    imageDomain = state.images[state.page],
                    onMainClickListener = onMainClickListener,
                    onDeleteClickListener = onDeleteClickListener,
                    onAddPhotoClickListener = onAddPhotoClickListener
                )
            },
            content = { paddingValues ->
                HorizontalPager(
                    count = state.images.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(image = state.images[it])
                        if (state.isLoading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        )

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                onImageSwipe(it)
            }
        }
    }

}

@Composable
private fun Image(image: ImageDomain) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = image.imageFile),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun BottomBar(
    imageDomain: ImageDomain,
    onMainClickListener: () -> Unit,
    onDeleteClickListener: () -> Unit,
    onAddPhotoClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(white)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_camera),
            contentDescription = null,
            colorFilter = ColorFilter.tint(psb1),
            modifier = Modifier.clickableUnbounded(onClick = onAddPhotoClickListener)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(),
                    onClick = onMainClickListener
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val starColor = if (imageDomain.isMainImage) AppTheme.colors.mainColor else psb4
            val text =
                stringResource(
                    if (imageDomain.isMainImage) {
                        R.string.image_viewer_main
                    } else {
                        R.string.image_viewer_make_main
                    }
                )

            Image(
                painter = painterResource(R.drawable.ic_star_stroke),
                contentDescription = null,
                colorFilter = ColorFilter.tint(starColor)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text, style = AppTheme.typography.body1, fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_trash),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(onClick = onDeleteClickListener)
        )
    }
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun ImageViewerScreenPreview() {
    ImageViewerScreen(ImageViewerStore.State(
        page = 4,
        images = listOf(
            ImageDomain(
                imageId = "",
                isMainImage = false
            ),
            ImageDomain(
                imageId = "",
                isMainImage = false
            ),
            ImageDomain(
                imageId = "",
                isMainImage = false
            ),
            ImageDomain(
                imageId = "",
                isMainImage = false
            ),
            ImageDomain(
                imageId = "",
                isMainImage = true
            ),
            ImageDomain(
                imageId = "",
                isMainImage = false
            ),
            ImageDomain(
                imageId = "",
                isMainImage = false
            ),
            ImageDomain(
                imageId = "",
                isMainImage = false
            ),
            ImageDomain(
                imageId = "",
                isMainImage = false
            ),
        )
    ), appInsets = AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {})
}
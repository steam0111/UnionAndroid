package com.itrocket.union.splash.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Bitmap
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.splash.presentation.store.SplashStore
import com.itrocket.union.theme.domain.entity.Medias
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.graphite5
import com.itrocket.union.ui.white

@Composable
fun SplashScreen(
    state: SplashStore.State,
    appInsets: AppInsets
) {

    val medias = state.medias
    when {
        medias != null -> {
            Splash(appInsets = appInsets, medias = medias)
        }
        else -> {
            //add design before splash loading
        }
    }
}

@Composable
private fun Splash(appInsets: AppInsets, medias: Medias) {
    AppTheme {
        Box(modifier = Modifier.background(AppTheme.colors.appBarBackgroundColor)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = appInsets.topInset.dp, bottom = appInsets.bottomInset.dp),
                Arrangement.SpaceBetween
            ) {
                Row {
                    Logo(header = medias.header)
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    CenterContent(logo = medias.logo)
                }
                Row(
                    Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Loader()
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    BottomContent()
                }
            }
        }
    }
}

@Composable
private fun Logo(header: ImageBitmap?) {
    Column {
        Spacer(modifier = Modifier.height(48.dp))
        if (header != null) {
            Image(
                bitmap = header,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 34.dp, end = 34.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_header),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 34.dp, end = 34.dp)
            )
        }
    }
}

@Composable
private fun CenterContent(logo: ImageBitmap?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (logo != null) {
            Image(
                bitmap = logo,
                contentDescription = null,
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.splash_description),
            color = white,
            style = AppTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun BottomContent() {
    Column {
        Text(
            text = stringResource(R.string.splash_company_title),
            color = graphite5,
            style = AppTheme.typography.caption,
            fontSize = 10.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_union_eam_logo),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 34.dp, end = 34.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun Loader() {
    val offset = 8.dp

    val offsetAnimation = remember {
        Animatable(0f)
    }
    LaunchedEffect(offsetAnimation) {
        offsetAnimation.animateTo(
            targetValue = offset.value * 2,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 300, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }
    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_small_progress_dot),
            contentDescription = "",
            modifier = Modifier
                .wrapContentSize()
                .absoluteOffset(y = (-offset.value + offsetAnimation.value).dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_small_progress_dot),
            contentDescription = "",
            modifier = Modifier
                .wrapContentSize()
                .absoluteOffset(y = (offset.value - offsetAnimation.value).dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_small_progress_dot),
            contentDescription = "",
            modifier = Modifier
                .wrapContentSize()
                .absoluteOffset(y = (-offset.value + offsetAnimation.value).dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_small_progress_dot),
            contentDescription = "",
            modifier = Modifier
                .wrapContentSize()
                .absoluteOffset(y = (offset.value - offsetAnimation.value).dp)
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
fun SplashScreenPreview() {
    SplashScreen(SplashStore.State(medias = Medias(null, null)), AppInsets())
}
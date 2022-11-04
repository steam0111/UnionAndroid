package com.itrocket.union.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R

@Composable
fun Loader(contentPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun DottedLoader(modifier: Modifier = Modifier, dotColor: Color = white) {
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
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_small_progress_dot),
            contentDescription = "",
            colorFilter = ColorFilter.tint(dotColor),
            modifier = Modifier
                .wrapContentSize()
                .absoluteOffset(y = (-offset.value + offsetAnimation.value).dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_small_progress_dot),
            contentDescription = "",
            colorFilter = ColorFilter.tint(dotColor),
            modifier = Modifier
                .wrapContentSize()
                .absoluteOffset(y = (offset.value - offsetAnimation.value).dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_small_progress_dot),
            contentDescription = "",
            colorFilter = ColorFilter.tint(dotColor),
            modifier = Modifier
                .wrapContentSize()
                .absoluteOffset(y = (-offset.value + offsetAnimation.value).dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_small_progress_dot),
            contentDescription = "",
            colorFilter = ColorFilter.tint(dotColor),
            modifier = Modifier
                .wrapContentSize()
                .absoluteOffset(y = (offset.value - offsetAnimation.value).dp)
        )
    }
}


@Preview
@Composable
private fun LoaderPreview() {
    Loader(contentPadding = PaddingValues())
}

@Preview
@Composable
private fun DottedLoaderPreview() {
    DottedLoader(
        Modifier
            .width(200.dp)
            .height(20.dp))
}
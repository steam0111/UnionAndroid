package com.itrocket.union.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R

@Composable
fun BaseButton(text: String, onClick: () -> Unit, modifier: Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = psb6,
            disabledBackgroundColor = graphite2
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
    ) {
        Text(
            text = text.uppercase(),
            style = AppTheme.typography.body2,
            color = white,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ImageButton(@DrawableRes imageId: Int, paddings: PaddingValues, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = null,
        modifier = Modifier
            .background(psb6, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick, interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple())
            .clip(RoundedCornerShape(8.dp))
            .padding(paddings)

    )
}

@Composable
fun ButtonWithContent(content: @Composable () -> Unit, onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = psb6),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isTextUpperCased: Boolean = true
) {
    Box(
        modifier = Modifier
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 12.dp)
    ) {
        Text(
            text = if (isTextUpperCased) {
                text.uppercase()
            } else {
                text
            }, color = if (enabled) {
                psb6
            } else {
                psb4
            }, style = AppTheme.typography.body2
        )
    }
}

@Composable
fun OutlinedImageButton(
    imageId: Int,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier,
    paddingValues: PaddingValues
) {
    Box(
        modifier = modifier
            .background(
                if (enabled) {
                    graphite2
                } else {
                    timberWolf
                }, RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp, color = if (enabled) {
                    psb6
                } else {
                    timberWolf
                }, shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick, enabled = enabled)
            .padding(paddingValues)
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                if (enabled) {
                    psb6
                } else {
                    white
                }
            )
        )
    }
}

@Composable
@Preview
fun ImageButtonPreview() {
    ImageButton(imageId = R.drawable.ic_camera, onClick = {}, paddings = PaddingValues(12.dp))
}

@Composable
@Preview
fun BaseButtonPreview() {
    BaseButton(text = "Title", onClick = { }, modifier = Modifier.fillMaxWidth())
}

@Composable
@Preview
fun OutlinedImageButtonPreview() {
    OutlinedImageButton(
        imageId = R.drawable.ic_arrow_back,
        onClick = { },
        modifier = Modifier,
        paddingValues = PaddingValues(20.dp),
        enabled = true
    )
}

@Composable
@Preview
fun TextButtonPreview() {
    TextButton(text = "Title", onClick = { })
}
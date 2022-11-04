package com.itrocket.union.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab

@Composable
fun BaseButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean = true,
    isAllUppercase: Boolean = true,
    backgroundColor: Color = AppTheme.colors.mainColor,
    disabledBackgroundColor: Color = graphite2
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledBackgroundColor
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
    ) {
        Text(
            text = if (isAllUppercase) {
                text.uppercase()
            } else {
                text
            },
            style = AppTheme.typography.body2,
            color = white,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    textStyle: TextStyle = AppTheme.typography.body2,
    textColor: Color = AppTheme.colors.mainColor,
    fontWeight: FontWeight = FontWeight.Medium,
    outlineColor: Color = AppTheme.colors.mainColor
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = white),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, outlineColor),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            fontWeight = fontWeight
        )
    }
}

@Composable
fun ImageButton(
    @DrawableRes imageId: Int,
    paddings: PaddingValues,
    onClick: () -> Unit,
    isEnabled: Boolean = true
) {
    val backgroundColor = if (isEnabled) AppTheme.colors.mainColor else graphite3

    Image(
        painter = painterResource(id = imageId),
        contentDescription = null,
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick, enabled = isEnabled, interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple())
            .clip(RoundedCornerShape(8.dp))
            .padding(paddings)

    )
}

@Composable
fun ButtonWithContent(
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    disabledBackgroundColor: Color = graphite3
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppTheme.colors.mainColor,
            disabledBackgroundColor = disabledBackgroundColor
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled,
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun ButtonWithLoader(
    onClick: () -> Unit,
    modifier: Modifier,
    isEnabled: Boolean,
    isLoading: Boolean,
    disabledBackgroundColor: Color = graphite3,
    content: @Composable () -> Unit,
) {
    ButtonWithContent(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        isEnabled = isEnabled,
        disabledBackgroundColor = disabledBackgroundColor,
        content = {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading) {
                    DottedLoader(modifier = Modifier.fillMaxHeight())
                } else {
                    content()
                }
            }
        },
    )
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
                AppTheme.colors.mainColor
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
                    white
                } else {
                    timberWolf
                }, RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp, color = if (enabled) {
                    AppTheme.colors.mainColor
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
                    AppTheme.colors.mainColor
                } else {
                    white
                }
            )
        )
    }
}

@Composable
fun ReadingModeButton(readingModeTab: ReadingModeTab, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(AppTheme.colors.mainColor, RoundedCornerShape(8.dp))
            .padding(
                start = 16.dp,
                top = 4.dp,
                end = 4.dp,
                bottom = 4.dp
            )
            .clickable(
                onClick = onClick,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple()
            )
    ) {
        Image(painter = painterResource(R.drawable.ic_reading), contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(readingModeTab.textId),
            style = AppTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(
                    white, RoundedCornerShape(6.dp)
                )
                .padding(vertical = 12.dp, horizontal = 17.dp)
        )
    }
}

@Composable
@Preview
fun ReadingModeButtonPreview() {
    ReadingModeButton(readingModeTab = ReadingModeTab.RFID) {

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
fun OutlinedButtonPreview() {
    OutlinedButton(text = "Title", onClick = { }, modifier = Modifier.fillMaxWidth())
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
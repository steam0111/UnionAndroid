package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.accountingObjects.domain.entity.Status

@Composable
fun SmallStatusLabel(
    status: Status,
    text: String?,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    BaseLabel(
        text = text,
        status = status,
        verticalPadding = 4.dp,
        horizontalPadding = 8.dp,
        textStyle = AppTheme.typography.caption,
        onClick = onClick,
        enabled = enabled
    )
}


@Composable
fun MediumStatusLabel(
    status: Status,
    text: String?,
    textStyle: TextStyle = AppTheme.typography.subtitle1
) {
    BaseLabel(
        text = text,
        status = status,
        verticalPadding = 8.dp,
        horizontalPadding = 16.dp,
        textStyle = textStyle
    )
}

@Composable
private fun BaseLabel(
    status: Status,
    text: String?,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    textStyle: TextStyle,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    Text(
        text = text ?: status.textId?.let { stringResource(it) } ?: status.text.orEmpty(),
        style = textStyle,
        color = Color(status.textColor.toULong()),
        modifier = Modifier
            .background(
                Color(status.backgroundColor.toULong()),
                RoundedCornerShape(111.dp)
            )
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClick,
                enabled = enabled
            )
            .padding(vertical = verticalPadding, horizontal = horizontalPadding)
    )
}

@Composable
@Preview
private fun PreviewLabels() {
    Column {
        SmallStatusLabel(
            ObjectStatusType(
                "",
                AppTheme.colors.secondaryColor.value.toString(),
                AppTheme.colors.secondaryColor.value.toString(),
                ""
            ), "available"
        )
        Spacer(modifier = Modifier.height(16.dp))
        MediumStatusLabel(
            ObjectStatusType(
                "",
                AppTheme.colors.secondaryColor.value.toString(),
                AppTheme.colors.secondaryColor.value.toString(),
                ""
            ), "available"
        )
    }
}
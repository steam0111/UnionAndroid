package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus

@Composable
fun SmallStatusLabel(objectStatus: ObjectStatus) {
    BaseLabel(
        objectStatus = objectStatus,
        verticalPadding = 4.dp,
        horizontalPadding = 8.dp,
        textStyle = AppTheme.typography.caption
    )
}

@Composable
fun MediumStatusLabel(
    objectStatus: ObjectStatus,
    textStyle: TextStyle = AppTheme.typography.subtitle1
) {
    BaseLabel(
        objectStatus = objectStatus,
        verticalPadding = 8.dp,
        horizontalPadding = 16.dp,
        textStyle = textStyle
    )
}

@Composable
private fun BaseLabel(
    objectStatus: ObjectStatus,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    textStyle: TextStyle
) {
    Text(
        text = stringResource(objectStatus.textId),
        style = textStyle,
        color = objectStatus.textColor,
        modifier = Modifier
            .background(
                objectStatus.backgroundColor,
                RoundedCornerShape(111.dp)
            )
            .padding(vertical = verticalPadding, horizontal = horizontalPadding)
    )
}

@Composable
@Preview
private fun PreviewLabels() {
    Column {
        SmallStatusLabel(ObjectStatus.AVAILABLE)
        Spacer(modifier = Modifier.height(16.dp))
        MediumStatusLabel(ObjectStatus.AVAILABLE)
    }
}
package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MediumSpacer() {
    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
            .background(graphite2)
    )
}

@Composable
fun SmallSpacer() {
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(graphite2)
    )
}

@Composable
@Preview
fun MediumSpacerPreview() {
    MediumSpacer()
}

@Composable
@Preview
fun SmallSpacerPreview() {
    SmallSpacer()
}
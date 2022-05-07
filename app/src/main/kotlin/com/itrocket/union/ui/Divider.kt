package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetDivider() {
    Spacer(
        modifier = Modifier
            .size(28.dp, 4.dp)
            .background(graphite3, RoundedCornerShape(12.dp))
    )
}

@Composable
@Preview
fun BottomSheetDividerPreview(){
    BottomSheetDivider()
}
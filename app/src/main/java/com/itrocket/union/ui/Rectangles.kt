package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomLine() {
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(psb4)
    )
}
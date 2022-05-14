package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets

@Composable
fun AuthContent(title: String, subtitle: String, fields: List<@Composable () -> Unit>) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 48.dp)
    ) {
        LogoToolbar()
        Spacer(Modifier.height(8.dp))
        Text(text = title, style = AppTheme.typography.h5, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text(text = subtitle, style = AppTheme.typography.body2, color = psb6)
        Spacer(Modifier.height(24.dp))
        fields.forEach {
            it()
        }
    }
}

@Composable
fun BottomSheetContainer(
    appInsets: AppInsets,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = appInsets.bottomInset.dp, top = appInsets.topInset.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(white, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(top = 16.dp),
        ) {
            content()
        }
    }
}
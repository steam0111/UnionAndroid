package com.itrocket.union.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.itrocket.union.theme.domain.entity.Medias

@Composable
fun AuthContent(
    title: String,
    subtitle: String,
    fields: List<@Composable () -> Unit>,
    medias: Medias?
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = AppTheme.dimens.auth_container_horizontal)
    ) {
        LogoToolbar(medias)
        Spacer(Modifier.height(8.dp))
        Text(text = title, style = AppTheme.typography.h5, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text(text = subtitle, style = AppTheme.typography.body2, color = AppTheme.colors.mainColor)
        Spacer(Modifier.height(24.dp))
        fields.forEach {
            it()
        }
    }
}
package com.itrocket.union.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BaseButton(text: String, onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = psb6),
        shape = RoundedCornerShape(8.dp),
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
@Preview
fun BaseButtonPreview() {
    BaseButton(text = "Title", onClick = { }, modifier = Modifier.fillMaxWidth())
}
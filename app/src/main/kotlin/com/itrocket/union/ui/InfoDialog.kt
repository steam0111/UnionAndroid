package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InfoDialog(
    title: String,
    confirmButtonText: String = "",
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(black_50)
            .clickable(
                onClick = onDismiss,
                indication = null,
                interactionSource = MutableInteractionSource()
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(white, RoundedCornerShape(8.dp)),
        ) {
            Content(title)
            if (confirmButtonText.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(
                        text = confirmButtonText, onClick = onDismiss, isTextUpperCased = false

                    )
                }
            }
        }
    }
}

@Composable
private fun Content(title: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = title)
    }
}

@Composable
@Preview
fun InfoDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        InfoDialog(title = "title", onDismiss = {}, confirmButtonText = "Ok")
    }
}
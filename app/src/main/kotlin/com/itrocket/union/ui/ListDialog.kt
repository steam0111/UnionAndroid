package com.itrocket.union.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R

@Composable
fun ListDialog(
    @StringRes titleRes: Int,
    listItems: List<String>,
    onDismiss: () -> Unit,
    isLoading: Boolean,
    confirmButtonText: String = ""
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Content(
                titleRes = titleRes,
                confirmButtonText = confirmButtonText,
                listItems = listItems,
                onDismiss = onDismiss,
                isLoading = isLoading
            )
        }
    }
}

@Composable
private fun Content(
    @StringRes titleRes: Int,
    listItems: List<String>,
    onDismiss: () -> Unit,
    isLoading: Boolean,
    confirmButtonText: String
) {
    val listPadding = if (confirmButtonText.isNotEmpty()) {
        32.dp
    } else {
        0.dp
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column {
            Text(
                text = stringResource(titleRes), style = TextStyle(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = listPadding)
            ) {
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                } else {
                    items(listItems) {
                        Text(
                            text = it.ifBlank { stringResource(R.string.value_not_defined) },
                            style = TextStyle(fontSize = 13.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        if (confirmButtonText.isNotEmpty()) {
            TextButton(text = confirmButtonText, onClick = onDismiss, isTextUpperCased = false)
        }
    }
}

@Composable
@Preview
fun ListDialogPreview() {
    Box(
        modifier = Modifier
            .padding(top = previewTopInsetDp.dp)
            .background(Color.White, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Content(
            titleRes = R.string.document_create_dialog_list_item_title,
            listItems = listOf(
                "123fsdf",
                "fnhdfjsdhfjsdhfsf",
                "fsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdf",
                "fsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdffsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdffsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdf",
                "123fsdf",
                "fnhdfjsdhfjsdhfsf",
                "fsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdf",
                "fsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdffsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdffsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdf",
                "123fsdf",
                "fnhdfjsdhfjsdhfsf",
                "fsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdf",
                "fsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdffsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdffsdjfhsdjfsdjkfhdjkfhsdfjkdsfhskdf",
            ),
            onDismiss = {},
            confirmButtonText = "Ok",
            isLoading = true
        )
    }
}
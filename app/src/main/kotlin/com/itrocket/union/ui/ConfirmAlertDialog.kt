package com.itrocket.union.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.itrocket.union.R

@Composable
fun ConfirmAlertDialog(
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit,
    confirmTextRes: Int = R.string.common_ok,
    dismissTextRes: Int = R.string.common_cancel,
    textRes: Int
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp), color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Content(
                    textRes = textRes,
                    confirmTextRes = confirmTextRes,
                    dismissTextRes = dismissTextRes,
                    onConfirmClick = onConfirmClick,
                    onDismiss = onDismiss
                )
            }
        }
    }
}

@Composable
fun ChooseAlertDialog(
    textRes: Int,
    actionResources: List<Int>,
    onActionClick: (Int) -> Unit
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp), color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                ActionContent(
                    textRes = textRes, actionResources = actionResources, onActionClick
                )
            }
        }
    }
}

@Composable
private fun ActionContent(
    textRes: Int, actionResources: List<Int>, onActionClick: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(24.dp, 12.dp, 12.dp, 8.dp)) {
            Text(
                text = stringResource(textRes), style = TextStyle(
                    fontSize = 14.sp, color = graphite8
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                actionResources.forEach { res ->
                    TextButton(
                        text = stringResource(res),
                        onClick = { onActionClick(res) },
                        isTextUpperCased = false
                    )
                }
            }
        }
    }
}

@Composable
private fun Content(
    textRes: Int,
    confirmTextRes: Int,
    dismissTextRes: Int,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(24.dp, 12.dp, 12.dp, 8.dp)) {
            Text(
                text = stringResource(textRes), style = TextStyle(
                    fontSize = 14.sp, color = graphite8
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    text = stringResource(dismissTextRes),
                    onClick = onDismiss,
                    isTextUpperCased = false
                )
                TextButton(
                    text = stringResource(confirmTextRes),
                    onClick = onConfirmClick,
                    isTextUpperCased = false
                )
            }
        }
    }
}

@Composable
@Preview
fun ConfirmAlertDialogContentPreview() {
    Box(
        modifier = Modifier.background(Color.White, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Content(
            textRes = R.string.common_confirm_save_text,
            onConfirmClick = {},
            onDismiss = {},
            confirmTextRes = R.string.common_ok,
            dismissTextRes = R.string.common_cancel
        )
    }
}
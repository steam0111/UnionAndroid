package com.itrocket.union.ui.listAction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.ButtonWithLoader
import com.itrocket.union.ui.white

@Composable
fun ListActionDialog(
    listDialogAction: List<DialogAction>,
    onDismiss: () -> Unit,
    loadingDialogActionType: DialogActionType?,
    onActionClick: (DialogActionType) -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Content(
                listDialogAction = listDialogAction,
                onActionClick = onActionClick,
                loadingDialogActionType = loadingDialogActionType
            )
        }
    }
}

@Composable
private fun Content(
    listDialogAction: List<DialogAction>,
    loadingDialogActionType: DialogActionType?,
    onActionClick: (DialogActionType) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .sizeIn(maxHeight = 300.dp)
    ) {
        itemsIndexed(listDialogAction) { index, item ->
            ButtonWithLoader(
                onClick = { onActionClick(item.type) },
                modifier = Modifier.fillMaxWidth(),
                isLoading = item.type == loadingDialogActionType,
                isEnabled = loadingDialogActionType == null,
                content = {
                    Text(
                        text = stringResource(item.actionTextId),
                        style = AppTheme.typography.body2,
                        color = white,
                        fontWeight = FontWeight.Medium
                    )
                },
            )
            if (listDialogAction.lastIndex != index) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
@Preview
fun ListActionDialogPreview() {
    Box(
        modifier = Modifier
            .padding(top = previewTopInsetDp.dp)
            .background(Color.White, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Content(
            listDialogAction = listOf(
                DialogAction(
                    type = DialogActionType.WRITE_OFF,
                    actionTextId = R.string.common_write_off
                ),
                DialogAction(
                    type = DialogActionType.WRITE_OFF,
                    actionTextId = R.string.common_write_off
                ),
                DialogAction(
                    type = DialogActionType.WRITE_OFF,
                    actionTextId = R.string.common_write_off
                )
            ),
            onActionClick = {},
            loadingDialogActionType = null
        )
    }
}
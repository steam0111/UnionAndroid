package com.itrocket.union.reserveDetail.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoBehavior
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.readerView.presentation.view.ReaderBottomBar
import com.itrocket.union.reserveDetail.presentation.store.ReserveDetailStore
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ExpandedInfoField
import com.itrocket.union.ui.InfoDialog
import com.itrocket.union.ui.LoadingContent
import com.itrocket.union.ui.black
import com.itrocket.union.ui.red5
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.clickableUnbounded
import java.math.BigDecimal

@Composable
fun ReserveDetailScreen(
    state: ReserveDetailStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onReadingModeClickListener: () -> Unit,
    onDocumentSearchClickListener: () -> Unit,
    onDocumentAddClickListener: () -> Unit,
    onMarkingClicked: () -> Unit,
    onWriteEpcDismiss: () -> Unit,
    onLabelTypeEditClickListener: () -> Unit,
) {
    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onBackClickListener = onBackClickListener,
                    onDocumentAddClickListener = onDocumentAddClickListener,
                    onDocumentSearchClickListener = onDocumentSearchClickListener
                )
            },
            bottomBar = {
                ReaderBottomBar(
                    selectedReadingMode = state.readingMode,
                    onReadingModeClickListener = onReadingModeClickListener,
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        ) {
            LoadingContent(isLoading = state.isLoading) {
                Column(modifier = Modifier.padding(it)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.reserve?.title.orEmpty(),
                        fontWeight = FontWeight.Bold,
                        style = AppTheme.typography.h6,
                        fontSize = 19.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ListInfo(
                        listInfo = state.reserve?.listInfo.orEmpty(),
                        showButtons = state.showButtons,
                        canUpdate = state.canUpdate,
                        onMarkingClicked = onMarkingClicked,
                        onLabelTypeEditClickListener = onLabelTypeEditClickListener
                    )
                }
            }
        }

        when (state.dialogType) {
            AlertType.WRITE_EPC -> {
                val title = state.rfidError.ifEmpty {
                    stringResource(R.string.common_write_epc_dialog_title)
                }
                val textColor = if (state.rfidError.isEmpty()) black else red5
                InfoDialog(
                    title = title,
                    textColor = textColor,
                    onDismiss = onWriteEpcDismiss
                )
            }
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onDocumentAddClickListener: () -> Unit,
    onDocumentSearchClickListener: () -> Unit
) {
    BaseToolbar(
        title = stringResource(id = R.string.reserves_title),
        startImageId = R.drawable.ic_cross,
        onStartImageClickListener = onBackClickListener,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_document_add),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
                    modifier = Modifier.clickableUnbounded(onClick = onDocumentAddClickListener)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_document_search),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
                    modifier = Modifier.clickableUnbounded(onClick = onDocumentSearchClickListener)
                )
            }
        }
    )
}

@Composable
private fun ListInfo(
    listInfo: List<ObjectInfoDomain>,
    showButtons: Boolean,
    canUpdate: Boolean,
    onMarkingClicked: () -> Unit,
    onLabelTypeEditClickListener: () -> Unit
) {
    LazyColumn {
        itemsIndexed(listInfo) { index, item ->
            val valueRes = item.valueRes?.let { stringResource(id = it) }.orEmpty()
            val label = item.name ?: item.title?.let { stringResource(id = it) }.orEmpty()
            val value = item.value.ifBlankOrNull { valueRes }

            when (item.fieldBehavior) {
                ObjectInfoBehavior.DEFAULT -> {
                    ExpandedInfoField(
                        label = label,
                        value = value,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                ObjectInfoBehavior.LABEL_TYPE -> {
                    ExpandedInfoField(
                        label = label,
                        value = value,
                        modifier = Modifier.fillMaxWidth(),
                        canEdit = item.canEdit,
                        onEditClickListener = onLabelTypeEditClickListener
                    )
                }
            }
        }
        if (showButtons && canUpdate) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                BaseButton(
                    text = stringResource(R.string.main_marking),
                    onClick = onMarkingClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun ReserveDetailScreenPreview() {
    ReserveDetailScreen(
        ReserveDetailStore.State(
            reserve = ReservesDomain(
                id = "1", title = "Авторучка «Зебра TR22»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = BigDecimal(1200L),
                barcodeValue = null,
                nomenclatureId = "",
                labelTypeId = "",
                consignment = "",
                unitPrice = "",
                bookKeepingInvoice = null
            )
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {}, {}, {})
}
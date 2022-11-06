package com.itrocket.union.accountingObjectDetail.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStore
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.ExpandedInfoField
import com.itrocket.union.ui.InfoDialog
import com.itrocket.union.ui.ReadingModeBottomBar
import com.itrocket.union.ui.white
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.clickableUnbounded

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AccountingObjectDetailScreen(
    state: AccountingObjectDetailStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onReadingModeClickListener: () -> Unit,
    onDocumentSearchClickListener: () -> Unit,
    onDocumentAddClickListener: () -> Unit,
    onPageChangeListener: (Int) -> Unit,
    onGenerateRfidClickListener: () -> Unit,
    onWriteEpcTagClickListener: () -> Unit,
    onWriteEpcDismiss: () -> Unit
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
                BottomBar(
                    onReadingModeClickListener = onReadingModeClickListener,
                    readingModeTab = state.readingMode
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        ) {
            Content(
                paddingValues = it,
                state = state,
                onGenerateRfidClickListener = onGenerateRfidClickListener,
                onWriteEpcTagClickListener = onWriteEpcTagClickListener
            )
        }
        when (state.dialogType) {
            AlertType.WRITE_EPC -> InfoDialog(
                title = state.rfidError.ifEmpty {
                    stringResource(R.string.common_write_epc_dialog_title)
                },
                onDismiss = onWriteEpcDismiss
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Content(
    paddingValues: PaddingValues,
    state: AccountingObjectDetailStore.State,
    onGenerateRfidClickListener: () -> Unit,
    onWriteEpcTagClickListener: () -> Unit
) {
    LazyColumn(Modifier.padding(top = paddingValues.calculateTopPadding())) {
        item {
            if (state.accountingObjectDomain.title.isNotEmpty()) {
                Text(
                    text = state.accountingObjectDomain.title,
                    fontWeight = FontWeight.Bold,
                    style = AppTheme.typography.h6,
                    fontSize = 19.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        items(state.accountingObjectDomain.listMainInfo) { item ->
            val valueRes = item.valueRes?.let { stringResource(id = it) }.orEmpty()
            ExpandedInfoField(
                label = item.name ?: item.title?.let { stringResource(id = it) }.orEmpty(),
                value = item.value.ifBlankOrNull { valueRes },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            BaseButton(
                text = stringResource(R.string.common_generate_rfid),
                onClick = onGenerateRfidClickListener,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            BaseButton(
                text = stringResource(R.string.common_write_epc),
                onClick = onWriteEpcTagClickListener,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onDocumentAddClickListener: () -> Unit,
    onDocumentSearchClickListener: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(4.dp)
            .background(AppTheme.colors.appBarBackgroundColor)
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_cross),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(
                onClick = onBackClickListener
            ),
            colorFilter = ColorFilter.tint(AppTheme.colors.mainColor)
        )
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = R.string.accounting_object_detail_title),
                modifier = Modifier.padding(start = 16.dp),
                style = AppTheme.typography.body1,
                fontWeight = FontWeight.Medium,
                lineHeight = 18.sp,
                color = white
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_document_add),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(onClick = onDocumentAddClickListener)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_document_search),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(onClick = onDocumentSearchClickListener)
        )
    }
}

@Composable
private fun BottomBar(
    readingModeTab: ReadingModeTab,
    onReadingModeClickListener: () -> Unit
) {
    ReadingModeBottomBar(
        readingModeTab = readingModeTab,
        onReadingModeClickListener = onReadingModeClickListener
    )
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
fun AccountingObjectDetailScreenPreview() {
    AccountingObjectDetailScreen(
        AccountingObjectDetailStore.State(
            accountingObjectDomain = AccountingObjectDomain(
                id = "123",
                title = "Ширикоформатный жидкокристалический монитор Samsung ЕК288, 23 дюйма и еще очень много текста текста",
                status = ObjectStatus("available"),
                isBarcode = false,
                listMainInfo = listOf(
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "длинный текст длинный текст  длинный текст  длинный текст  длинный текст  длинный текст длинный текст  длинный текст  длинный текст "
                    ),
                    ObjectInfoDomain(R.string.auth_main_title, "blabla2")
                ),
                listAdditionallyInfo = listOf(
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "длинный текст длинный текст  длинный текст  длинный текст  длинный текст  длинный текст длинный текст  длинный текст  длинный текст "
                    ),
                    ObjectInfoDomain(R.string.auth_main_title, "blabla2")
                ),
                inventoryStatus = InventoryAccountingObjectStatus.NOT_FOUND,
                barcodeValue = "",
                rfidValue = "",
                factoryNumber = ""
            ),
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {}, {}, {}, {})
}
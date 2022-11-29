package com.itrocket.union.accountingObjectDetail.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.ui.BaseTab
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStore
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.image.ImageDomain
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.ExpandedInfoField
import com.itrocket.union.ui.InfoDialog
import com.itrocket.union.ui.ReadingModeBottomBar
import com.itrocket.union.ui.graphite3
import com.itrocket.union.ui.image.GridImages
import com.itrocket.union.ui.white
import com.itrocket.union.utils.ifBlankOrNull
import com.itrocket.utils.clickableUnbounded
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    onWriteEpcDismiss: () -> Unit,
    onWriteOffClickListener: () -> Unit,
    onRemoveRfidClickListener: () -> Unit,
    onRemoveBarcodeClickListener: () -> Unit,
    onImageClickListener: (ImageDomain) -> Unit,
    onAddImageClickListener: () -> Unit,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        BaseTab(
            title = stringResource(R.string.accounting_object_detail_main),
            screen = {
                ListInfo(
                    listInfo = state.accountingObjectDomain.listMainInfo,
                    state = state,
                    onGenerateRfidClickListener = onGenerateRfidClickListener,
                    onWriteEpcTagClickListener = onWriteEpcTagClickListener,
                    onWriteOffClickListener = onWriteOffClickListener,
                    onRemoveRfidClickListener = onRemoveRfidClickListener,
                    onRemoveBarcodeClickListener = onRemoveBarcodeClickListener,
                    showButtons = true
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.accounting_object_detail_additionally),
            screen = {
                ListInfo(
                    listInfo = state.accountingObjectDomain.listAdditionallyInfo,
                    state = state
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.accounting_object_detail_characteristic),
            screen = {
                ListInfo(
                    listInfo = state.accountingObjectDomain.characteristics,
                    state = state
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.accounting_object_detail_photo),
            screen = {
                GridImages(
                    images = state.accountingObjectDomain.images,
                    onImageClickListener = onImageClickListener,
                    canAddImage = true,
                    onAddImageClickListener = onAddImageClickListener
                )
            }
        )
    )
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
                tabs = tabs,
                onTabClickListener = onPageChangeListener,
                coroutineScope = coroutineScope,
                pagerState = pagerState,
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
    tabs: List<BaseTab>,
    pagerState: PagerState,
    onTabClickListener: (Int) -> Unit,
    coroutineScope: CoroutineScope,
) {
    Column(
        Modifier
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        Header(
            tabs = tabs,
            pagerState = pagerState,
            selectedPage = state.selectedPage,
            accountingObjectItem = state.accountingObjectDomain,
            onTabClickListener = onTabClickListener,
            coroutineScope = coroutineScope
        )
        HorizontalPager(count = tabs.size, state = pagerState) { page ->
            tabs[page].screen()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Header(
    tabs: List<BaseTab>,
    pagerState: PagerState,
    selectedPage: Int,
    accountingObjectItem: AccountingObjectDomain,
    coroutineScope: CoroutineScope,
    onTabClickListener: (Int) -> Unit
) {
    Column {
        if (accountingObjectItem.title.isNotEmpty()) {
            Text(
                text = accountingObjectItem.title,
                fontWeight = FontWeight.Bold,
                style = AppTheme.typography.h6,
                fontSize = 19.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        AccountingObjectsTabRow(
            selectedTabIndex = selectedPage, tabs = tabs, onTabClick = {
                onTabClickListener(it)
                // todo сделать без вызова метода с анимейт скроллом. Это должно работать отдельно
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            },
            pagerState = pagerState
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(graphite3)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AccountingObjectsTabRow(
    tabs: List<BaseTab>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    enabled: Boolean = true,
    pagerState: PagerState
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = white,
        contentColor = white,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                color = AppTheme.colors.appBarBackgroundColor
            )
        }
    ) {
        tabs.forEachIndexed { tabIndex, tab ->
            Text(
                text = tab.title,
                style = AppTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                color = AppTheme.colors.mainTextColor,
                modifier = Modifier
                    .padding(6.dp)
                    .clickable(enabled = enabled) {
                        onTabClick(tabIndex)
                    }
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun ListInfo(
    listInfo: List<ObjectInfoDomain>,
    state: AccountingObjectDetailStore.State,
    onGenerateRfidClickListener: () -> Unit = {},
    onWriteEpcTagClickListener: () -> Unit = {},
    onWriteOffClickListener: () -> Unit = {},
    onRemoveRfidClickListener: () -> Unit = {},
    onRemoveBarcodeClickListener: () -> Unit = {},
    showButtons: Boolean = false
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(listInfo) { index, item ->
            val valueRes = item.valueRes?.let { stringResource(id = it) }.orEmpty()
            ExpandedInfoField(
                label = item.name ?: item.title?.let { stringResource(id = it) }.orEmpty(),
                value = item.value.ifBlankOrNull { valueRes },
                modifier = Modifier.fillMaxWidth()
            )
            if (showButtons && index == listInfo.lastIndex) {
                when {
                    !state.isLoading && state.canUpdate -> {
                        Spacer(modifier = Modifier.height(12.dp))
                        BaseButton(
                            text = stringResource(R.string.common_generate_rfid),
                            onClick = onGenerateRfidClickListener,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        BaseButton(
                            text = stringResource(R.string.common_write_epc),
                            onClick = onWriteEpcTagClickListener,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                        if (state.accountingObjectDomain.forWrittenOff != true) {
                            Spacer(modifier = Modifier.height(12.dp))
                            BaseButton(
                                text = stringResource(R.string.common_write_off),
                                onClick = onWriteOffClickListener,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                        if (state.accountingObjectDomain.rfidValue != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            BaseButton(
                                text = stringResource(R.string.common_remove_rfid),
                                onClick = onRemoveRfidClickListener,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                        if (state.accountingObjectDomain.barcodeValue != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            BaseButton(
                                text = stringResource(R.string.common_remove_barcode),
                                onClick = onRemoveBarcodeClickListener,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    !state.isLoading -> {
                        Spacer(modifier = Modifier.height(12.dp))
                        BaseButton(
                            text = stringResource(R.string.common_write_epc),
                            onClick = onWriteEpcTagClickListener,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
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
                characteristics = emptyList(),
                inventoryStatus = InventoryAccountingObjectStatus.NOT_FOUND,
                barcodeValue = "",
                rfidValue = "",
                factoryNumber = "",
                marked = true,
            ),
        ),
        AppInsets(topInset = previewTopInsetDp),
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {})
}
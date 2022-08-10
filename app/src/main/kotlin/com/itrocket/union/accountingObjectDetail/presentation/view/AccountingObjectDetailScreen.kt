package com.itrocket.union.accountingObjectDetail.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.ui.BaseTab
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.presentation.store.AccountingObjectDetailStore
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.inventoryCreate.domain.entity.InventoryAccountingObjectStatus
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.ExpandedInfoField
import com.itrocket.union.ui.ReadingModeBottomBar
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AccountingObjectDetailScreen(
    state: AccountingObjectDetailStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onReadingModeClickListener: () -> Unit,
    onDocumentSearchClickListener: () -> Unit,
    onDocumentAddClickListener: () -> Unit,
    onPageChangeListener: (Int) -> Unit
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        BaseTab(
            title = stringResource(R.string.accounting_object_detail_main),
            screen = {
                ListInfo(state.accountingObjectDomain.listMainInfo)
            }
        ),
        BaseTab(
            title = stringResource(R.string.accounting_object_detail_additionally),
            screen = {
                ListInfo(state.accountingObjectDomain.listAdditionallyInfo)
            }
        )
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            onPageChangeListener(it)
        }
    }
    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    accountingObjectName = state.accountingObjectDomain.title,
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
                pagerState = pagerState,
                tabs = tabs,
                onTabClickListener = onPageChangeListener,
                coroutineScope = coroutineScope
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
    coroutineScope: CoroutineScope,
    onTabClickListener: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(paddingValues)) {
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
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = accountingObjectItem.title,
            fontWeight = FontWeight.Bold,
            style = AppTheme.typography.h6,
            fontSize = 19.sp,
        )
        //TODO: Пока не нужен
        /*Spacer(modifier = Modifier.height(16.dp))
        DoubleTabRow(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .border(
                    width = 1.dp,
                    color = graphite2,
                    shape = RoundedCornerShape(8.dp)
                ),
            selectedPage = selectedPage,
            targetPage = pagerState.getTargetPage(),
            tabs = tabs,
            onTabClickListener = {
                onTabClickListener(it)
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            },
            tabIndicator = {
                TabIndicatorBlack(tabPositions = it, pagerState = pagerState)
            }
        )*/
    }
}

@Composable
private fun Toolbar(
    accountingObjectName: String,
    onBackClickListener: () -> Unit,
    onDocumentAddClickListener: () -> Unit,
    onDocumentSearchClickListener: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(4.dp)
            .background(psb1)
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_cross),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(
                onClick = onBackClickListener
            )
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

@Composable
private fun ListInfo(listInfo: List<ObjectInfoDomain>) {
    LazyColumn {
        items(listInfo) {
            ExpandedInfoField(
                label = stringResource(id = it.title),
                value = it.value.orEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
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
fun AccountingObjectDetailScreenPreview() {
    AccountingObjectDetailScreen(
        AccountingObjectDetailStore.State(
            accountingObjectDomain = AccountingObjectDomain(
                id = "123",
                title = "Ширикоформатный жидкокристалический монитор Samsung ЕК288, 23 дюйма и еще очень много текста текста",
                status = ObjectStatus("available", ObjectStatusType.AVAILABLE),
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
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {})
}
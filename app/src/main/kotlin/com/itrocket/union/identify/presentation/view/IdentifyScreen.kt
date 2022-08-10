package com.itrocket.union.identify.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.BaseTab
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.identify.presentation.store.IdentifyStore
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.DoubleTabRow
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.ReadingModeBottomBar
import com.itrocket.union.ui.TabIndicatorBlack
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IdentifyScreen(
    state: IdentifyStore.State,
    appInsets: AppInsets,
    onReadingModeClickListener: () -> Unit,
    onBackClickListener: () -> Unit,
    onObjectClickListener: (AccountingObjectDomain) -> Unit,
    onDropClickListener: () -> Unit,
    onPageChanged: (Int) -> Unit
) {
    val pagerState = rememberPagerState(state.selectedPage)
    val coroutineScope = rememberCoroutineScope()

    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onBackClickListener = onBackClickListener,
                    onDropClickListener = onDropClickListener
                )
            },
            bottomBar = {
                BottomBar(
                    readingModeTab = state.readingModeTab,
                    onReadingModeClickListener = onReadingModeClickListener
                )
            },
            content = {
                Content(
                    onTabClickListener = onPageChanged,
                    pagerState = pagerState,
                    selectedPage = state.selectedPage,
                    coroutineScope = coroutineScope,
                    state = state,
                    onObjectClickListener = onObjectClickListener,
                    onReadingModeClickListener = onReadingModeClickListener,
                    paddingValues = it
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        )
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            onPageChanged(it)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Content(
    onTabClickListener: (Int) -> Unit,
    coroutineScope: CoroutineScope,
    selectedPage: Int,
    pagerState: PagerState,
    state: IdentifyStore.State,
    onReadingModeClickListener: () -> Unit,
    onObjectClickListener: (AccountingObjectDomain) -> Unit,
    paddingValues: PaddingValues
) {
    val tabs = listOf(
        BaseTab(
            title = (stringResource(R.string.os_tab_title, state.accountingObjects.size)),
            screen = {
                if (state.accountingObjects.isNotEmpty()) {
                    ScanningObjectsScreen(
                        accountingObjects = state.accountingObjects,
                        onObjectClickListener = onObjectClickListener,
                        paddingValues = paddingValues
                    )
                } else EmptyListStub(paddingValues = PaddingValues())
            }
        )
    )
    Column {
        DoubleTabRow(
            modifier = Modifier
                .padding(16.dp)
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
        )
        MediumSpacer()
        HorizontalPager(count = tabs.size, state = pagerState, modifier = Modifier) { page ->
            tabs[page].screen()
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
) {
    BaseToolbar(
        title = stringResource(id = R.string.identify_title),
        onStartImageClickListener = onBackClickListener,
        startImageId = R.drawable.ic_arrow_back,
        backgroundColor = psb1,
        textColor = white,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_delete),
                    contentDescription = null,
                    modifier = Modifier.clickableUnbounded(onClick = onDropClickListener)
                )
            }
        }
    )
}

@Composable
private fun ScanningObjectsScreen(
    accountingObjects: List<AccountingObjectDomain>,
    onObjectClickListener: (AccountingObjectDomain) -> Unit,
    paddingValues: PaddingValues
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        itemsIndexed(accountingObjects, key = { _, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = accountingObjects.lastIndex != index
            AccountingObjectItem(
                accountingObject = item,
                onAccountingObjectListener = onObjectClickListener,
                status = item.status?.type,
                isShowBottomLine = isShowBottomLine,
                statusText = item.status?.text
            )
        }
        item {
            Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun EmptyListStub(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_reader),
            contentDescription = null,
            colorFilter = ColorFilter.tint(graphite4)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.text_begin_identify),
            style = AppTheme.typography.body1,
            color = graphite4,
            textAlign = TextAlign.Center
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

@Preview
@Composable
fun IdentifyScreenPreview() {
    IdentifyScreen(
        state = IdentifyStore.State(
            accountingObjects = listOf(
                AccountingObjectDomain(
                    id = "1",
                    isBarcode = true,
                    title = "Ширикоформатный жидкокристалический монитор Samsung",
                    status = ObjectStatus("Доступен", ObjectStatusType.AVAILABLE),
                    listMainInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                    ),
                    listAdditionallyInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                    ),
                    barcodeValue = "",
                    rfidValue = ""
                ),
                AccountingObjectDomain(
                    id = "2",
                    isBarcode = true,
                    title = "Ширикоформатный жидкокристалический монитор Samsung",
                    status = ObjectStatus("Доступен", ObjectStatusType.DECOMMISSIONED),
                    listMainInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                    ),
                    listAdditionallyInfo = listOf(),
                    barcodeValue = "",
                    rfidValue = ""
                ), AccountingObjectDomain(
                    id = "3",
                    isBarcode = true,
                    title = "Ширикоформатный жидкокристалический монитор Samsung",
                    status = ObjectStatus("Доступен", ObjectStatusType.REPAIR),
                    listMainInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "таылватвлыавыалвыоалвыа"
                        ),
                    ),
                    listAdditionallyInfo = listOf(),
                    barcodeValue = "",
                    rfidValue = ""
                )
            ),
            selectedPage = 1,
            readingModeTab = ReadingModeTab.RFID
        ),
        appInsets = AppInsets(),
        onReadingModeClickListener = {},
        onBackClickListener = {},
        onObjectClickListener = {},
        onDropClickListener = {},
        onPageChanged = {}
    )
}
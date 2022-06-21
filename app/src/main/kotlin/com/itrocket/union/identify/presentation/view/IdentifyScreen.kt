package com.itrocket.union.identify.presentation.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.BaseTab
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.identify.presentation.store.IdentifyStore
import com.itrocket.union.ui.*
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun IdentifyScreen(
    state: IdentifyStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        BaseTab(
            title = stringResource(R.string.accounting_object_detail_main),
            screen = {

                    state.accountingObjects.isNotEmpty() -> {
                AccountingObjectList(
                    accountingObjects = state.accountingObjects,
                    navBarPadding = appInsets.bottomInset,
                    onAccountingObjectListener = {
                        onAccountingObjectListener(it)


                    }

            }
//                    state.accountingObjectDomain.getShortAdditionallyInfoList()
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.accounting_object_detail_additionally),
            screen = {
                ListInfo(
//                    state.accountingObjectDomain.getShortAdditionallyInfoList()
                )
            }
        )
    )

    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            BlackToolbar(
                title = stringResource(id = R.string.identify_title),
                onBackClickListener = onBackClickListener,
            )
//            Column(modifier = Modifier.padding(paddingValues)) {
//                Header(
//                    tabs = tabs,
//                    pagerState = pagerState,
//                    selectedPage = state.selectedPage,
//                    accountingObjectItem = state.accountingObjectDomain,
//                    onTabClickListener = onTabClickListener,
//                    coroutineScope = coroutineScope
//                )
//
//                HorizontalPager(count = tabs.size, state = pagerState) { page ->
//                    tabs[page].screen()
//                }
//                when {
//                    state.isLoading -> {
//                        Column(
//                            modifier = Modifier.fillMaxSize(),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            CircularProgressIndicator()
//                        }
//                    }
//                    state.identifies.isNotEmpty() -> {
//                        IdentifyList(
//                            identifies = state.identifies,
//                            navBarPadding = appInsets.bottomInset,
//                            onIdentifyListener = {
//                                onIdentifyListener(it)
//                            }
//                        )
//                    }
//                }
//            }
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
            fontWeight = FontWeight.Medium,
            style = AppTheme.typography.body1,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
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
        )
    }
}

@Composable
private fun IdentifyList(
    identifies: List<AccountingObjectDomain>,
    navBarPadding: Int,
    onIdentifyListener: (AccountingObjectDomain) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(identifies, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = identifies.lastIndex != index
            IdentifyItem(
                identifies = item,
                onIdentifyListener = onIdentifyListener,
                isShowBottomLine = isShowBottomLine
            )
        }
        item {
            Spacer(modifier = Modifier.height(navBarPadding.dp))
        }
    }
}

@Composable
private fun ListInfo(listInfo: List<ObjectInfoDomain>) {
    LazyColumn {
        items(listInfo) {
            ExpandedInfoField(
                label = it.title,
                value = it.value,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

//    @ExperimentalPagerApi
//    @Preview(
//        name = "светлая тема экран - 6.3 (3040x1440)",
//        showSystemUi = true,
//        device = Devices.PIXEL_4_XL,
//        uiMode = Configuration.UI_MODE_NIGHT_NO
//    )
//    @Preview(
//        name = "темная тема экран - 4,95 (1920 × 1080)",
//        showSystemUi = true,
//        device = Devices.NEXUS_5,
//        uiMode = Configuration.UI_MODE_NIGHT_YES
//    )
//    @Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
//    @Composable
//    fun IdentifyScreenPreview() {
//        IdentifyScreen(
//            IdentifyStore.State(
//                                identifies = listOf(
//                    AccountingObjectDomain(
//                        id = "7",
//                        isBarcode = true,
//                        title = "Ширикоформатный жидкокристалический монитор Samsung2",
//
//                        status = ObjectStatus.AVAILABLE,
//                        listMainInfo = listOf(
//                            ObjectInfoDomain(
//                                "Заводской номер",
//                                "таылватвлыавыалвыоалвыа"
//                            ),
//                            ObjectInfoDomain(
//                                "Инвентарный номер",
//                                "таылватвлыавыалвыоалвыа"
//                            ),
//                        ),
//                        listAdditionallyInfo = listOf()
//                    ),
//                    AccountingObjectDomain(
//                        id = "8",
//                        isBarcode = true,
//                        title = "Ширикоформатный жидкокристалический монитор Samsung2",
//                        status = ObjectStatus.AVAILABLE,
//                        listMainInfo = listOf(
//                            ObjectInfoDomain(
//                                "Заводской номер",
//                                "таылватвлыавыалвыоалвыа"
//                            ),
//                            ObjectInfoDomain(
//                                "Инвентарный номер",
//                                "таылватвлыавыалвыоалвыа"
//                            ),
//                        ),
//                        listAdditionallyInfo = listOf()
//                    )
//                )
//            ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {})
//    }
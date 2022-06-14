package com.itrocket.union.inventoryReserves.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.ui.BaseTab
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.inventoryReserves.presentation.store.InventoryReservesStore
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ButtonBottomBar
import com.itrocket.union.ui.DoubleTabRow
import com.itrocket.union.ui.Loader
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.ReservesItem
import com.itrocket.union.ui.SelectedBaseField
import com.itrocket.union.ui.TabIndicatorBlack
import com.itrocket.union.ui.UnselectedBaseField
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.psb6
import com.itrocket.union.ui.white
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun InventoryReservesScreen(
    state: InventoryReservesStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    onInventoryCreateClickListener: () -> Unit,
    onPageChanged: (Int) -> Unit,
    onParamClickListener: (ParamDomain) -> Unit,
    onParamCrossClickListener: (ParamDomain) -> Unit,
    onReservesClickListener: (ReservesDomain) -> Unit
) {
    val pagerState = rememberPagerState(state.selectedPage)
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        BaseTab(
            title = stringResource(R.string.inventory_params),
            screen = {
                ParamContent(
                    onParamClickListener = onParamClickListener,
                    params = state.params,
                    onCrossClickListener = onParamCrossClickListener
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.inventory_ao_title),
            screen = {
                ReservesScreen(
                    isLoading = state.isLoading,
                    reserves = state.reserves,
                    onReservesClickListener = onReservesClickListener
                )
            }
        )
    )

    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onDropClickListener = onDropClickListener,
                    onBackClickListener = onBackClickListener
                )
            },
            bottomBar = {
                ButtonBottomBar(
                    onClick = onInventoryCreateClickListener,
                    text = stringResource(R.string.common_create)
                )
            },
            content = {
                Content(
                    onTabClickListener = onPageChanged,
                    pagerState = pagerState,
                    selectedPage = state.selectedPage,
                    coroutineScope = coroutineScope,
                    tabs = tabs
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
    tabs: List<BaseTab>
) {
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
        HorizontalPager(count = tabs.size, state = pagerState) { page ->
            tabs[page].screen()
        }
    }
}

@Composable
private fun ParamContent(
    params: List<ParamDomain>,
    onParamClickListener: (ParamDomain) -> Unit,
    onCrossClickListener: (ParamDomain) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(params, key = {
            it.type
        }) {
            if (!it.paramValue?.value.isNullOrBlank()) {
                SelectedBaseField(
                    label = stringResource(it.type.titleId),
                    value = it.paramValue?.value.orEmpty(),
                    onFieldClickListener = {
                        onParamClickListener(it)
                    },
                    isCrossVisible = true,
                    onCrossClickListener = {
                        onCrossClickListener(it)
                    }
                )
            } else {
                UnselectedBaseField(
                    label = stringResource(it.type.titleId),
                    onFieldClickListener = {
                        onParamClickListener(it)
                    })
            }
        }
    }
}

@Composable
private fun ReservesScreen(
    isLoading: Boolean,
    reserves: List<ReservesDomain>,
    onReservesClickListener: (ReservesDomain) -> Unit
) {
    if (isLoading) {
        Loader(contentPadding = PaddingValues())
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(reserves, key = { index, item ->
                item.id
            }) { index, item ->
                val isShowBottomLine = reserves.lastIndex != index
                ReservesItem(
                    reserves = item,
                    onReservesListener = onReservesClickListener,
                    isShowBottomLine = isShowBottomLine
                )
            }
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
) {
    BaseToolbar(
        title = stringResource(id = R.string.inventory_reserves_title),
        onStartImageClickListener = onBackClickListener,
        startImageId = R.drawable.ic_arrow_back,
        backgroundColor = psb1,
        textColor = white,
        content = {
            Text(
                text = stringResource(R.string.common_drop),
                style = AppTheme.typography.body2,
                color = psb6,
                modifier = Modifier.clickable(onClick = onDropClickListener)
            )
        }
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
fun InventoryReservesScreenPreview() {
    InventoryReservesScreen(InventoryReservesStore.State(
        reserves = listOf(
            ReservesDomain(
                id = "1", title = "Авторучка «Зебра TR22»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = 1200
            ),
            ReservesDomain(
                id = "2",
                title = "Бумага А4 «Русалочка-500 листов»",
                isBarcode = false,
                listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ),
                itemsCount = 56
            ),
            ReservesDomain(
                id = "3",
                title = "Бумага А4 «Русалочка-250 листов»",
                isBarcode = true,
                listInfo =
                listOf(
                    ObjectInfoDomain(
                        "Заводской номер",
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        "Инвентарный номер",
                        "таылватвлыавыалвыоалвыа"
                    )
                ),
                itemsCount = 167
            )
        )
    ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {}, {}, {})
}
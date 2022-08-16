package com.itrocket.union.inventory.presentation.view

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
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatusType
import com.itrocket.union.inventory.presentation.store.InventoryStore
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ButtonBottomBar
import com.itrocket.union.ui.DoubleTabRow
import com.itrocket.union.ui.Loader
import com.itrocket.union.ui.MediumSpacer
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
fun InventoryScreen(
    state: InventoryStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    onInventoryCreateClickListener: () -> Unit,
    onPageChanged: (Int) -> Unit,
    onParamClickListener: (ParamDomain) -> Unit,
    onParamCrossClickListener: (ParamDomain) -> Unit
) {
    val pagerState = rememberPagerState(state.selectedPage)
    val coroutineScope = rememberCoroutineScope()

    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onDropClickListener = onDropClickListener,
                    onBackClickListener = onBackClickListener
                )
            },
            bottomBar = {
                if (state.isCanCreateInventory) {
                    ButtonBottomBar(
                        onClick = onInventoryCreateClickListener,
                        text = stringResource(R.string.common_create),
                        isLoading = state.isCreateInventoryLoading,
                    )
                }
            },
            content = {
                Content(
                    onTabClickListener = onPageChanged,
                    pagerState = pagerState,
                    selectedPage = state.selectedPage,
                    coroutineScope = coroutineScope,
                    onParamCrossClickListener = onParamCrossClickListener,
                    onParamClickListener = onParamClickListener,
                    paddingValues = it,
                    state = state
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
    state: InventoryStore.State,
    onTabClickListener: (Int) -> Unit,
    onParamClickListener: (ParamDomain) -> Unit,
    onParamCrossClickListener: (ParamDomain) -> Unit,
    coroutineScope: CoroutineScope,
    selectedPage: Int,
    pagerState: PagerState,
    paddingValues: PaddingValues
) {
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
            title = stringResource(R.string.inventory_accounting_object),
            screen = {
                AccountingObjectScreen(
                    isLoading = state.isAccountingObjectsLoading,
                    accountingObjectList = state.accountingObjectList,
                    onAccountingObjectClickListener = {},
                    paddingValues = paddingValues
                )
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
            if (it.value.isNotBlank()) {
                SelectedBaseField(
                    label = stringResource(it.type.titleId),
                    value = it.value,
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
private fun AccountingObjectScreen(
    isLoading: Boolean,
    accountingObjectList: List<AccountingObjectDomain>,
    onAccountingObjectClickListener: (AccountingObjectDomain) -> Unit,
    paddingValues: PaddingValues
) {
    if (isLoading) {
        Loader(contentPadding = PaddingValues())
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            itemsIndexed(accountingObjectList, key = { index, item ->
                item.id
            }) { index, item ->
                val isShowBottomLine = accountingObjectList.lastIndex != index
                AccountingObjectItem(
                    accountingObject = item,
                    onAccountingObjectListener = onAccountingObjectClickListener,
                    isShowBottomLine = isShowBottomLine,
                    status = item.status?.type
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
        title = stringResource(id = R.string.inventory_ao_title),
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
fun InventoryScreenPreview() {
    InventoryScreen(
        InventoryStore.State(
            params = listOf(
                StructuralParamDomain(manualType = ManualType.STRUCTURAL),
                ParamDomain(
                    "2",
                    "Колесников Виталий Константинович ",
                    type = ManualType.MOL
                ),
                ParamDomain("3", "", type = ManualType.LOCATION_INVENTORY),
            ),
            accountingObjectList = listOf(
                AccountingObjectDomain(
                    id = "1",
                    isBarcode = true,
                    title = "Ширикоформатный жидкокристалический монитор Samsung",
                    status = ObjectStatus("AVAILABLE", ObjectStatusType.AVAILABLE),
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
                    rfidValue = "",
                    factoryNumber = ""
                ),
                AccountingObjectDomain(
                    id = "2",
                    isBarcode = true,
                    title = "Ширикоформатный жидкокристалический монитор Samsung",
                    status = ObjectStatus("AVAILABLE", ObjectStatusType.DECOMMISSIONED),
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
                    rfidValue = "",
                    factoryNumber = ""
                ), AccountingObjectDomain(
                    id = "3",
                    isBarcode = true,
                    title = "Ширикоформатный жидкокристалический монитор Samsung",
                    status = ObjectStatus("AVAILABLE", ObjectStatusType.REPAIR),
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
                    rfidValue = "",
                    factoryNumber = ""
                )
            ),
            selectedPage = 1,
        ),
        AppInsets(topInset = previewTopInsetDp),
        {},
        {},
        {},
        {},
        {},
        {},
    )
}
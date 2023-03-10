package com.itrocket.union.identify.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
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
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.identify.domain.NomenclatureReserveDomain
import com.itrocket.union.identify.presentation.store.IdentifyStore
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.DoubleTabRow
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.NomenclatureReserveItem
import com.itrocket.union.ui.ReadingModeBottomBar
import com.itrocket.union.ui.TabRowIndicator
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.listAction.DialogActionType
import com.itrocket.union.ui.listAction.ListActionDialog
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.CoroutineScope
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
    onPageChanged: (Int) -> Unit,
    onPlusClickListener: () -> Unit,
    onListActionDialogDismissed: () -> Unit,
    onListActionDialogClick: (DialogActionType) -> Unit,
    onNomenclatureReserveClickListener: (NomenclatureReserveDomain) -> Unit
) {
    val pagerState = rememberPagerState(state.selectedPage)
    val coroutineScope = rememberCoroutineScope()

    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    canUpdateAccountingObjects = state.canUpdateAccountingObjects,
                    onBackClickListener = onBackClickListener,
                    onDropClickListener = onDropClickListener,
                    onPlusClickListener = onPlusClickListener
                )
            },
            bottomBar = {
                BottomBar(
                    readingModeTab = state.readingModeTab,
                    onReadingModeClickListener = onReadingModeClickListener,
                    rfidLevel = state.readerPower
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
                    onNomenclatureReserveClickListener = onNomenclatureReserveClickListener,
                    paddingValues = it
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        )
    }

    if (state.dialogType == AlertType.LIST_ACTION) {
        ListActionDialog(
            listDialogAction = state.listDialogAction,
            onDismiss = onListActionDialogDismissed,
            onActionClick = onListActionDialogClick,
            loadingDialogActionType = state.loadingDialogAction,
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
    onNomenclatureReserveClickListener: (NomenclatureReserveDomain) -> Unit,
    paddingValues: PaddingValues
) {
    val tabs = listOf(
        BaseTab(
            title = (stringResource(R.string.os_tab_title, state.accountingObjects.size)),
            screen = {
                if (state.accountingObjects.isNotEmpty()) {
                    AccountingObjectsScreen(
                        accountingObjects = state.accountingObjects,
                        onObjectClickListener = onObjectClickListener,
                        paddingValues = paddingValues
                    )
                } else EmptyListStub(paddingValues = PaddingValues())
            }
        ),
        BaseTab(
            title = (stringResource(R.string.reserve_tab_title, state.nomenclatureReserves.size)),
            screen = {
                if (state.nomenclatureReserves.isNotEmpty()) {
                    NomenclatureReserveScreen(
                        nomenclatures = state.nomenclatureReserves,
                        onItemClickListener = onNomenclatureReserveClickListener,
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
                TabRowIndicator(tabPositions = it, pagerState = pagerState)
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
    canUpdateAccountingObjects: Boolean,
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    onPlusClickListener: () -> Unit
) {
    BaseToolbar(
        title = stringResource(id = R.string.identify_title),
        onStartImageClickListener = onBackClickListener,
        startImageId = R.drawable.ic_arrow_back,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_delete),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
                    modifier = Modifier.clickableUnbounded(onClick = onDropClickListener)
                )
                if (canUpdateAccountingObjects) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(AppTheme.colors.mainColor),
                        modifier = Modifier.clickableUnbounded(onClick = onPlusClickListener)
                    )
                }
            }
        }
    )
}

@Composable
private fun AccountingObjectsScreen(
    accountingObjects: List<AccountingObjectDomain>,
    onObjectClickListener: (AccountingObjectDomain) -> Unit,
    paddingValues: PaddingValues
) {
    key(accountingObjects.size) {
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
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun NomenclatureReserveScreen(
    nomenclatures: List<NomenclatureReserveDomain>,
    onItemClickListener: (NomenclatureReserveDomain) -> Unit,
    paddingValues: PaddingValues
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        itemsIndexed(nomenclatures, key = { _, item ->
            item.nomenclatureId
        }) { index, item ->
            val isShowBottomLine = nomenclatures.lastIndex != index
            NomenclatureReserveItem(
                nomenclatureReserveDomain = item,
                isShowBottomLine = isShowBottomLine,
                onClick = onItemClickListener
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
    onReadingModeClickListener: () -> Unit,
    rfidLevel: Int?
) {
    ReadingModeBottomBar(
        readingModeTab = readingModeTab,
        onReadingModeClickListener = onReadingModeClickListener,
        rfidLevel = rfidLevel
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
                    title = "?????????????????????????????? ?????????????????????????????????????? ?????????????? Samsung",
                    status = ObjectStatus("????????????????"),
                    listMainInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                    ),
                    listAdditionallyInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                    ),
                    barcodeValue = "",
                    rfidValue = "",
                    factoryNumber = "",
                    marked = true,
                    characteristics = emptyList(),
                ),
                AccountingObjectDomain(
                    id = "2",
                    isBarcode = true,
                    title = "?????????????????????????????? ?????????????????????????????????????? ?????????????? Samsung",
                    status = ObjectStatus("????????????????"),
                    listMainInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                    ),
                    listAdditionallyInfo = listOf(),
                    barcodeValue = "",
                    rfidValue = "",
                    factoryNumber = "",
                    marked = true,
                    characteristics = emptyList(),
                ), AccountingObjectDomain(
                    id = "3",
                    isBarcode = true,
                    title = "?????????????????????????????? ?????????????????????????????????????? ?????????????? Samsung",
                    status = ObjectStatus("????????????????"),
                    listMainInfo = listOf(
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                        ObjectInfoDomain(
                            R.string.auth_main_title,
                            "??????????????????????????????????????????????"
                        ),
                    ),
                    listAdditionallyInfo = listOf(),
                    barcodeValue = "",
                    rfidValue = "",
                    factoryNumber = "",
                    marked = true,
                    characteristics = emptyList(),
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
        onPageChanged = {},
        onPlusClickListener = {},
        onListActionDialogDismissed = {},
        onListActionDialogClick = {},
        onNomenclatureReserveClickListener = {}
    )
}
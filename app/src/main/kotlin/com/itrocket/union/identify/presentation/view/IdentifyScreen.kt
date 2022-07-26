package com.itrocket.union.identify.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.BaseTab
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.identify.domain.entity.OSandReserves
import com.itrocket.union.identify.presentation.store.IdentifyStore
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.ui.*
import com.itrocket.utils.clickableUnbounded
import com.itrocket.utils.getTargetPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun IdentifyScreen(
    state: IdentifyStore.State,
    appInsets: AppInsets,
    onReadingModeClickListener: () -> Unit,
    onBackClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onObjectClickListener: (OSandReserves) -> Unit,
//    onReservesClickListener: (ReservesDomain) -> Unit,
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
                    onSaveClickListener = onSaveClickListener,
                    onDropClickListener = onDropClickListener
                )
            },
            bottomBar = {
                BottomBar(
                    onReadingModeClickListener = onReadingModeClickListener
                )
            },
            content = { it ->
                Content(
                    onTabClickListener = onPageChanged,
                    pagerState = pagerState,
                    selectedPage = state.selectedPage,
                    coroutineScope = coroutineScope,
                    paddingValues = it,
                    state = state,
                    onObjectClickListener = {
                        onObjectClickListener(it)
                    },
                    isLoading = state.isBottomActionMenuLoading
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
    state: IdentifyStore.State,
    onTabClickListener: (Int) -> Unit,
    coroutineScope: CoroutineScope,
    onObjectClickListener: (OSandReserves) -> Unit,
//    onReservesClickListener: (ReservesDomain) -> Unit,
    selectedPage: Int,
    pagerState: PagerState,
    paddingValues: PaddingValues,
    isLoading: Boolean
) {
    val tabs = listOf(
        BaseTab(
            title = (stringResource(R.string.documents_main_assets) + "(" + state.os.size + ")"),
            screen = {
                when {
                    state.os.isNotEmpty() -> {
                        ObjectList(
                            items = state.os,
                            onObjectClickListener = onObjectClickListener
                        )
                    }
                    state.os.isEmpty() -> {
                        ObjectListEmpty(paddingValues = PaddingValues())
                    }
                }
            }
        ),
        BaseTab(
            title = stringResource(R.string.documents_reserves) + "(" + state.reserves.size + ")",
            screen = {
                when {
                    state.reserves.isNotEmpty() -> {
                        ObjectList(
                            items = state.reserves,
                            onObjectClickListener = onObjectClickListener
                        )
                    }
                    state.reserves.isEmpty() -> {
                        ObjectListEmpty(paddingValues = PaddingValues())
                    }
                }

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
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
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
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = null,
                    modifier = Modifier.clickableUnbounded(onClick = onSaveClickListener)
                )
                Spacer(modifier = Modifier.width(24.dp))
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
private fun ObjectList(
    items: List<OSandReserves>,
    onObjectClickListener: (OSandReserves) -> Unit
) {

    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(items, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = items.lastIndex != index
            when (item) {
                is AccountingObjectDomain -> {
                    AccountingObjectItem(
                        accountingObject = item,
                        onAccountingObjectListener = onObjectClickListener,
                        status = null,
                        isShowBottomLine = isShowBottomLine
                    )
                }
                is ReservesDomain -> {
                    ReservesItem(
                        reserves = item,
                        onReservesListener = onObjectClickListener,
                        isShowBottomLine = isShowBottomLine
                    )
                }
            }

        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Composable
private fun ObjectListEmpty(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
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
    onReadingModeClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(16.dp)
    ) {
        BaseButton(
            text = stringResource(R.string.accounting_object_detail_reading_mode),
            onClick = onReadingModeClickListener,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
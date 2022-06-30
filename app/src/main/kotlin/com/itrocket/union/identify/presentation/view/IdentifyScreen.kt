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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.BaseTab
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
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
    onOSClickListener: (AccountingObjectDomain) -> Unit,
    onReservesClickListener: (ReservesDomain) -> Unit,
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
//                ButtonBottomBar(
//                    onClick = onReadingModeClickListener,
//                    text = stringResource(R.string.accounting_object_detail_reading_mode),
//                )
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
                    onIdentifyClickListener = onOSClickListener,
                    onReservesClickListener = {
                        onReservesClickListener(it)
                    }
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
    onIdentifyClickListener: (AccountingObjectDomain) -> Unit,
    onReservesClickListener: (ReservesDomain) -> Unit,
    selectedPage: Int,
    pagerState: PagerState,
    paddingValues: PaddingValues
) {
    val tabs = listOf(
        BaseTab(
            title = (stringResource(R.string.documents_main_assets) + "(" + state.identifies.size + ")"),
            screen = {
                IdentifyList(
                    state = state,
                    identifies = state.identifies,
                    navBarPadding = 0,
                    onIdentifyListener = onIdentifyClickListener
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.documents_reserves) + "(" + state.reserves.size + ")",
            screen = {
                when {
                    state.reserves.isNotEmpty() -> {
                        ReservesList(
                            reserves = state.reserves,
                            navBarPadding = 1,
                            onReservesListener = onReservesClickListener
                        )
                    }
                    state.reserves.isEmpty() -> {
                        ReservesListEmpty()
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
private fun IdentifyList(
    state: IdentifyStore.State,
    identifies: List<AccountingObjectDomain>,
    navBarPadding: Int,
    onIdentifyListener: (AccountingObjectDomain) -> Unit
) {
    if (navBarPadding != 0) {//TODO Изменить на реальное условие 
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(painter = painterResource(id = R.drawable.ic_tsd), contentDescription = null)
            Text( //TODO Добавить шрифт Roboto
                text = stringResource(R.string.text_begin_identify),
                modifier = Modifier
                    .width(217.dp)
                    .size(16.dp)
                    .weight(400F),
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.main_gray)
//                color = Color(red = 0x84, green = 0x84, blue = 0x8E)
            )
        }

    } else {
        LazyColumn(Modifier.fillMaxSize()) {
            itemsIndexed(identifies, key = { index, item ->
                item.id
            }) { index, item ->
                val isShowBottomLine = identifies.lastIndex != index
                IdentifyItem(
                    identifies = item,
                    onIdentifyListener = onIdentifyListener,
                    isShowBottomLine = isShowBottomLine,
                    status = item.status?.type
                )
            }
            item {
                Spacer(modifier = Modifier.height(navBarPadding.dp))
            }
        }
    }
}

@Composable
private fun ReservesList(
    reserves: List<ReservesDomain>,
    navBarPadding: Int,
    onReservesListener: (ReservesDomain) -> Unit
) {

    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(reserves, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = reserves.lastIndex != index
            ReservesItem(
                reserves = item,
                onReservesListener = onReservesListener,
                isShowBottomLine = isShowBottomLine
            )
        }
        item {
            Spacer(modifier = Modifier.height(navBarPadding.dp))
        }
    }
}


@Composable
private fun ReservesListEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.ic_tsd), contentDescription = null)
        Text( //TODO Добавить шрифт Roboto
            text = stringResource(R.string.text_begin_identify),
            modifier = Modifier
                .width(217.dp)
                .size(16.dp)
                .weight(400F),
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.main_gray)
        )
    }
}

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        NavigationItem.OpenItem,
        NavigationItem.CreateDoc,
        NavigationItem.DeleteItem
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.design_default_color_primary),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_tsd),
                        contentDescription = ""
                    )
                },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f)
            )
        }

    }
}

sealed class NavigationItem(var title: String) {
    object OpenItem : NavigationItem("Открыть карточку")
    object CreateDoc : NavigationItem("Создать документ")
    object DeleteItem : NavigationItem("Удалить из списка")
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
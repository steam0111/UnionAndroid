package com.itrocket.union.identify.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.BaseTab
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.identify.presentation.store.IdentifyStore
import com.itrocket.union.ui.*
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

@Composable
fun OsScreen(
    state: IdentifyStore.State,
    onObjectClickListener: (AccountingObjectDomain) -> Unit,
    onReadingModeClickListener: () -> Unit,
    paddingValues: PaddingValues
) {
    Scaffold(
        content = {
            when {
                state.os.isNotEmpty() -> {
                    AccountingObjectScreen(
                        accountingObjects = state.os,
                        onObjectClickListener = onObjectClickListener,
                        paddingValues = paddingValues
                    )
                }
                state.os.isEmpty() -> {
                    ObjectListEmpty(paddingValues = PaddingValues())
                }
            }
        }
    )
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
            title = (stringResource(R.string.documents_main_assets) + " (" + state.os.size + ")"),
            screen = {
                OsScreen(
                    onReadingModeClickListener = onReadingModeClickListener,
                    onObjectClickListener = onObjectClickListener,
                    state = state,
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
private fun AccountingObjectScreen(
    accountingObjects: List<AccountingObjectDomain>,
    onObjectClickListener: (AccountingObjectDomain) -> Unit,
    paddingValues: PaddingValues
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
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
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun ObjectListEmpty(paddingValues: PaddingValues) {
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
    onReadingModeClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseButton(
            text = stringResource(R.string.accounting_object_detail_reading_mode),
            onClick = onReadingModeClickListener,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
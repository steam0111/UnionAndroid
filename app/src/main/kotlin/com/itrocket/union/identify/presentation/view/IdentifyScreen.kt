package com.itrocket.union.identify.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.itrocket.union.identify.presentation.store.IdentifyStore
import com.itrocket.union.manual.ParamDomain
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
    onIdentifyClickListener: (AccountingObjectDomain) -> Unit,
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
                ButtonBottomBar(
                    onClick = onReadingModeClickListener,
                    text = stringResource(R.string.accounting_object_detail_reading_mode),
                )
            },
            content = {
                Content(
                    onTabClickListener = onPageChanged,
                    pagerState = pagerState,
                    selectedPage = state.selectedPage,
                    coroutineScope = coroutineScope,
                    paddingValues = it,
                    state = state,
                    onIdentifyClickListener = onIdentifyClickListener
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
    selectedPage: Int,
    pagerState: PagerState,
    paddingValues: PaddingValues
) {
    val tabs = listOf(
        BaseTab(
            title = stringResource(R.string.documents_main_assets),
            screen = {
                IdentifyList(
                    identifies = state.accountingObjects,
                    navBarPadding = 0,
                    onIdentifyListener = onIdentifyClickListener
                )
            }
        ),
        BaseTab(
            title = stringResource(R.string.documents_reserves),
            screen = {
                IdentifyList(
                    identifies = state.accountingObjects,
                    navBarPadding = 1,
                    onIdentifyListener = onIdentifyClickListener
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
                    painter = painterResource(id = R.drawable.ic_document_add),
                    contentDescription = null,
                    modifier = Modifier.clickableUnbounded(onClick = onSaveClickListener)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_document_search),
                    contentDescription = null,
                    modifier = Modifier.clickableUnbounded(onClick = onDropClickListener)
                )
            }
        }
    )
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
                isShowBottomLine = isShowBottomLine,
                status = item.status?.type
            )
        }
        item {
            Spacer(modifier = Modifier.height(navBarPadding.dp))
        }
    }
}
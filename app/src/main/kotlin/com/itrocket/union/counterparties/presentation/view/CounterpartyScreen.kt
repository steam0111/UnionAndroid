package com.itrocket.union.counterparties.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain
import com.itrocket.union.counterparties.domain.entity.toDefaultItem
import com.itrocket.union.counterparties.presentation.store.CounterpartyStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.DefaultListItem
import com.itrocket.union.ui.LoadingContent
import com.itrocket.union.ui.SearchToolbar
import com.itrocket.utils.paging.subscribePagingListIndex

@Composable
fun CounterpartyScreen(
    state: CounterpartyStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onCounterpartyClickListener: (CounterpartyDomain) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchClickListener: () -> Unit,
    onLoadNext: () -> Unit
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            SearchToolbar(
                title = stringResource(id = R.string.counterparties_title),
                onBackClickListener = onBackClickListener,
                onSearchClickListener = onSearchClickListener,
                onSearchTextChanged = onSearchTextChanged,
                isShowSearch = state.isShowSearch,
                searchText = state.searchText
            )
            Content(
                counterparties = state.counterparties,
                onCounterpartyClickListener = onCounterpartyClickListener,
                navBarPadding = appInsets.bottomInset,
                onLoadNext = onLoadNext,
                isLoading = state.isLoading,
                isEndReached = state.isListEndReached
            )
        }
    }
}

@Composable
private fun Content(
    counterparties: List<CounterpartyDomain>,
    onCounterpartyClickListener: (CounterpartyDomain) -> Unit,
    navBarPadding: Int,
    isLoading: Boolean,
    isEndReached: Boolean,
    onLoadNext: () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        itemsIndexed(counterparties, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = counterparties.lastIndex != index
            DefaultListItem(
                item = item.toDefaultItem(),
                onItemClickListener = {
                    onCounterpartyClickListener(item)
                },
                isShowBottomLine = isShowBottomLine
            )
        }
        item {
            Spacer(modifier = Modifier.height(navBarPadding.dp))
        }
        if (isLoading) {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
    LaunchedEffect(listState.firstVisibleItemIndex) {
        subscribePagingListIndex(
            listState = listState,
            listSize = counterparties.size,
            isListEndReached = isEndReached,
            isLoading = isLoading,
            onLoadNext = onLoadNext
        )
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
fun CounterpartyScreenPreview() {
    CounterpartyScreen(CounterpartyStore.State(
        counterparties = listOf(
            CounterpartyDomain(
                id = "1",
                catalogItemName = "blb",
                name = "fsdfs",
                actualAddress = "ffsdf",
                legalAddress = "fsdfsd",
                inn = "f123",
                kpp = "345345"
            ),
            CounterpartyDomain(
                id = "2",
                catalogItemName = "blb",
                name = "fsdfs",
                actualAddress = "ffsdf",
                legalAddress = "fsdfsd",
                inn = "f123",
                kpp = "345345"
            ),
            CounterpartyDomain(
                id = "3",
                catalogItemName = "blb",
                name = "fsdfs",
                actualAddress = "ffsdf",
                legalAddress = "fsdfsd",
                inn = "f123",
                kpp = "345345"
            ),
        )
    ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {})
}
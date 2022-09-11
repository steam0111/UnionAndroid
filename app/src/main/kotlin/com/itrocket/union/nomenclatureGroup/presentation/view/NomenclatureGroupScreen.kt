package com.itrocket.union.nomenclatureGroup.presentation.view

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
import com.itrocket.union.R
import com.itrocket.union.nomenclatureGroup.domain.entity.NomenclatureGroupDomain
import com.itrocket.union.nomenclatureGroup.domain.entity.toDefaultItem
import com.itrocket.union.nomenclatureGroup.presentation.store.NomenclatureGroupStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.DefaultListItem
import com.itrocket.union.ui.LoadingContent
import com.itrocket.union.ui.SearchToolbar
import com.itrocket.utils.paging.subscribePagingListIndex

@Composable
fun NomenclatureGroupScreen(
    state: NomenclatureGroupStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onItemClick: (String) -> Unit,
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
                title = stringResource(id = R.string.nomenclature_group_title),
                onBackClickListener = onBackClickListener,
                onSearchClickListener = onSearchClickListener,
                onSearchTextChanged = onSearchTextChanged,
                isShowSearch = state.isShowSearch,
                searchText = state.searchText
            )
            Content(
                nomenclatureGroupsDomain = state.nomenclatureGroups,
                navBarPadding = appInsets.bottomInset,
                onItemClick = onItemClick,
                onLoadNext = onLoadNext,
                isLoading = state.isLoading,
                isEndReached = state.isListEndReached
            )
        }
    }
}

@Composable
private fun Content(
    nomenclatureGroupsDomain: List<NomenclatureGroupDomain>,
    navBarPadding: Int,
    onItemClick: (String) -> Unit,
    isLoading: Boolean,
    isEndReached: Boolean,
    onLoadNext: () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        itemsIndexed(nomenclatureGroupsDomain, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = nomenclatureGroupsDomain.lastIndex != index
            DefaultListItem(
                item = item.toDefaultItem(),
                onItemClickListener = { onItemClick(it.id) },
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
            listSize = nomenclatureGroupsDomain.size,
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
fun NomenclatureGroupScreenPreview() {
    NomenclatureGroupScreen(NomenclatureGroupStore.State(
        nomenclatureGroups = listOf(
            NomenclatureGroupDomain(
                "0",
                "name"
            ),
            NomenclatureGroupDomain(
                "1",
                "name 1"
            ),
            NomenclatureGroupDomain(
                "2",
                "name 2"
            ),
            NomenclatureGroupDomain(
                "3",
                "name 3"
            )
        )
    ), AppInsets(), {}, {}, {}, {}, {})
}
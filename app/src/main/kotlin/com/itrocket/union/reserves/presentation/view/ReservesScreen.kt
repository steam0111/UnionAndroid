package com.itrocket.union.reserves.presentation.view

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
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.manual.isFilterApplied
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.reserves.presentation.store.ReservesStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.ReservesItem
import com.itrocket.union.ui.SearchToolbar
import com.itrocket.union.ui.listAction.PropertyInfoAlertDialog
import com.itrocket.utils.paging.subscribePagingListIndex
import java.math.BigDecimal

@Composable
fun ReservesScreen(
    state: ReservesStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit,
    onFilterClickListener: () -> Unit,
    onReservesListener: (ReservesDomain) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onLoadNext: () -> Unit,
    onInfoClicked: () -> Unit,
    onDialogDismiss: () -> Unit
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            SearchToolbar(
                title = stringResource(id = R.string.reserves_title),
                onBackClickListener = onBackClickListener,
                onSearchClickListener = onSearchClickListener,
                onFilterClickListener = onFilterClickListener,
                onSearchTextChanged = onSearchTextChanged,
                isShowSearch = state.isShowSearch,
                searchText = state.searchText,
                isFilterApplied = state.params.isFilterApplied(),
                onInfoClickListener = onInfoClicked
            )
            ReservesList(
                reserves = state.reserves,
                navBarPadding = appInsets.bottomInset,
                onReservesListener = {
                    onReservesListener(it)
                },
                onLoadNext = onLoadNext,
                isLoading = state.isLoading,
                isEndReached = state.isListEndReached
            )
        }
        if (state.isInfoDialogVisible) {
            PropertyInfoAlertDialog(
                onDismiss = onDialogDismiss,
                text = stringResource(
                    id = R.string.property_info_text,
                    state.positionsCount,
                    state.allCount
                )
            )
        }
    }
}


@Composable
private fun ReservesList(
    reserves: List<ReservesDomain>,
    navBarPadding: Int,
    onReservesListener: (ReservesDomain) -> Unit,
    isLoading: Boolean,
    isEndReached: Boolean,
    onLoadNext: () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
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
        item {
            Spacer(modifier = Modifier.height(navBarPadding.dp))
        }
    }
    LaunchedEffect(listState.firstVisibleItemIndex) {
        subscribePagingListIndex(
            listState = listState,
            listSize = reserves.size,
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
fun ReservesScreenPreview() {
    ReservesScreen(ReservesStore.State(
        reserves = listOf(
            ReservesDomain(
                id = "1", title = "Авторучка «Зебра TR22»", isBarcode = true, listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    )
                ), itemsCount = BigDecimal(1200L),
                barcodeValue = "",
                labelTypeId = "",
                nomenclatureId = "",
                consignment = ""
            ),
            ReservesDomain(
                id = "2",
                title = "Бумага А4 «Русалочка-500 листов»",
                isBarcode = false,
                listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    )
                ),
                itemsCount = BigDecimal(1200L),
                barcodeValue = "",
                labelTypeId = "",
                nomenclatureId = "",
                consignment = ""
            ),
            ReservesDomain(
                id = "3",
                title = "Бумага А4 «Русалочка-250 листов»",
                isBarcode = true,
                listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    )
                ),
                itemsCount = BigDecimal(1200L),
                barcodeValue = "",
                labelTypeId = "",
                nomenclatureId = "",
                consignment = ""
            )
        ),
        params = listOf()
    ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {}, {}, {}, {})
}
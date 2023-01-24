package com.itrocket.union.accountingObjects.presentation.view

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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.AccountingObjectDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectStatus
import com.itrocket.union.accountingObjects.presentation.store.AccountingObjectStore
import com.itrocket.union.manual.isFilterApplied
import com.itrocket.union.ui.AccountingObjectItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.SearchToolbar
import com.itrocket.union.ui.listAction.PropertyInfoAlertDialog
import com.itrocket.utils.paging.subscribePagingListIndex

@ExperimentalPagerApi
@Composable
fun AccountingObjectScreen(
    state: AccountingObjectStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit,
    onFilterClickListener: () -> Unit,
    onAccountingObjectListener: (AccountingObjectDomain) -> Unit,
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
                title = stringResource(id = R.string.accounting_objects_title),
                onBackClickListener = onBackClickListener,
                onSearchClickListener = onSearchClickListener,
                onFilterClickListener = onFilterClickListener,
                onInfoClickListener = onInfoClicked,
                isShowSearch = state.isShowSearch,
                onSearchTextChanged = onSearchTextChanged,
                searchText = state.searchText,
                isFilterApplied = state.params.isFilterApplied()
            )
            AccountingObjectList(
                accountingObjects = state.accountingObjects,
                navBarPadding = appInsets.bottomInset,
                onAccountingObjectListener = {
                    onAccountingObjectListener(it)
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
                    id = R.string.property_info_text_position_count,
                    state.positionsCount
                )
            )
        }
    }
}

@Composable
private fun AccountingObjectList(
    accountingObjects: List<AccountingObjectDomain>,
    navBarPadding: Int,
    isLoading: Boolean,
    isEndReached: Boolean,
    onAccountingObjectListener: (AccountingObjectDomain) -> Unit,
    onLoadNext: () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        itemsIndexed(accountingObjects, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = accountingObjects.lastIndex != index
            AccountingObjectItem(
                accountingObject = item,
                onAccountingObjectListener = onAccountingObjectListener,
                isShowBottomLine = isShowBottomLine,
                status = item.status?.type,
                statusText = item.status?.text
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
            listSize = accountingObjects.size,
            isListEndReached = isEndReached,
            isLoading = isLoading,
            onLoadNext = onLoadNext
        )
    }
}

@ExperimentalPagerApi
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
fun AccountingObjectScreenPreview() {
    AccountingObjectScreen(
        AccountingObjectStore.State(
            accountingObjects = listOf(
                AccountingObjectDomain(
                    id = "7",
                    isBarcode = true,
                    title = "Ширикоформатный жидкокристалический монитор Samsung2",
                    status = null,
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
                    factoryNumber = "",
                    marked = false,
                    characteristics = emptyList(),
                ),
                AccountingObjectDomain(
                    id = "8",
                    isBarcode = true,
                    title = "Ширикоформатный жидкокристалический монитор Samsung2",
                    status = ObjectStatus("available"),
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
                    factoryNumber = "",
                    marked = true,
                    characteristics = emptyList(),
                )
            ),
            params = emptyList()
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {}, {}, {}, {})
}
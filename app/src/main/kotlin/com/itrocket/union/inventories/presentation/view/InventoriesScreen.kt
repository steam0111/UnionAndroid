package com.itrocket.union.inventories.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
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
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventories.presentation.store.InventoriesStore
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.isFilterApplied
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BottomLine
import com.itrocket.union.ui.InventoryDocumentItem
import com.itrocket.union.ui.SearchToolbar
import com.itrocket.utils.paging.subscribePagingListIndex

@Composable
fun InventoriesScreen(
    state: InventoriesStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit,
    onFilterClickListener: () -> Unit,
    onInventoryClickListener: (InventoryCreateDomain) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onLoadNext: () -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                SearchToolbar(
                    title = stringResource(
                        id = R.string.inventories_title,
                    ),
                    onSearchClickListener = onSearchClickListener,
                    onBackClickListener = onBackClickListener,
                    onFilterClickListener = onFilterClickListener,
                    onSearchTextChanged = onSearchTextChanged,
                    isShowSearch = state.isShowSearch,
                    searchText = state.searchText,
                    isFilterApplied = state.params.isFilterApplied()
                )
            },
            content = {
                Content(
                    contentPadding = it,
                    inventories = state.inventories,
                    onInventoryClickListener = onInventoryClickListener,
                    onLoadNext = onLoadNext,
                    isLoading = state.isLoading,
                    isEndReached = state.isListEndReached
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp, bottom = appInsets.bottomInset.dp)
        )
    }
}

@Composable
private fun Content(
    contentPadding: PaddingValues,
    inventories: List<InventoryCreateDomain>,
    onInventoryClickListener: (InventoryCreateDomain) -> Unit,
    isLoading: Boolean,
    isEndReached: Boolean,
    onLoadNext: () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        itemsIndexed(inventories) { index, item ->
            Column {
                InventoryDocumentItem(
                    item = item,
                    enabled = true,
                    onInventoryClickListener = {
                        onInventoryClickListener(item)
                    },
                    isShowStatus = true
                )
                if (inventories.lastIndex != index) {
                    BottomLine()
                }
            }
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
            listSize = inventories.size,
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
fun InventoriesScreenPreview() {
    InventoriesScreen(
        InventoriesStore.State(
            inventories = listOf(
                InventoryCreateDomain(
                    id = "bh",
                    number = "БП-00001375",
                    creationDate = System.currentTimeMillis(),
                    documentInfo = listOf(
                        ParamDomain("1", "Систмный интегратор", ManualType.MOL),
                        ParamDomain("2", "Систмный интегратор", ManualType.MOL),
                        ParamDomain("3", "Систмный интегратор", ManualType.MOL),
                    ),
                    accountingObjects = listOf(),
                    inventoryStatus = InventoryStatus.CREATED,
                    userInserted = "",
                    userUpdated = "",
                    nomenclatureRecords = listOf()
                ),
                InventoryCreateDomain(
                    id = "",
                    number = "БП-00001376",
                    creationDate = System.currentTimeMillis(),
                    documentInfo = listOf(
                        ParamDomain("1", "Систмный интегратор", ManualType.MOL),
                        ParamDomain("2", "Систмный интегратор", ManualType.MOL),
                        ParamDomain("3", "Систмный интегратор", ManualType.MOL),
                    ),
                    accountingObjects = listOf(),
                    inventoryStatus = InventoryStatus.CREATED,
                    userInserted = "",
                    userUpdated = "",
                    nomenclatureRecords = listOf()
                ),
                InventoryCreateDomain(
                    id = "",
                    number = "БП-00001377",
                    creationDate = System.currentTimeMillis(),
                    documentInfo = listOf(
                        ParamDomain("1", "Систмный интегратор", ManualType.MOL),
                        ParamDomain("2", "Систмный интегратор", ManualType.MOL),
                        ParamDomain("3", "Систмный интегратор", ManualType.MOL),
                    ),
                    accountingObjects = listOf(),
                    inventoryStatus = InventoryStatus.CREATED,
                    userInserted = "",
                    userUpdated = "",
                    nomenclatureRecords = listOf()
                )
            )
        ),
        AppInsets(topInset = previewTopInsetDp),
        {},
        {},
        {},
        {},
        {},
        {}
    )
}
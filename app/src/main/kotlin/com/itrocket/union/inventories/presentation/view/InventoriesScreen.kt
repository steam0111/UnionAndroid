package com.itrocket.union.inventories.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.inventories.presentation.store.InventoriesStore
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.BottomLine
import com.itrocket.union.ui.InventoryDocumentItem
import com.itrocket.union.ui.LoadingContent

@Composable
fun InventoriesScreen(
    state: InventoriesStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit,
    onFilterClickListener: () -> Unit,
    onInventoryClickListener: (InventoryCreateDomain) -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                BlackToolbar(
                    title = stringResource(
                        id = R.string.inventories_title,
                    ),
                    onSearchClickListener = onSearchClickListener,
                    onBackClickListener = onBackClickListener,
                    onFilterClickListener = onFilterClickListener
                )
            },
            content = {
                LoadingContent(isLoading = state.isLoading) {
                    Content(
                        contentPadding = it,
                        inventories = state.inventories,
                        onInventoryClickListener = onInventoryClickListener
                    )
                }
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
) {
    LazyColumn(
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
                    }
                )
                if (inventories.lastIndex != index) {
                    BottomLine()
                }
            }
        }
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
                    number = "БП-00001375",
                    time = "12:40",
                    date = "12.12.12",
                    documentInfo = listOf(
                        "Систмный интегратор",
                        "Систмный интегратор",
                        "Систмный интегратор",
                        "Систмный интегратор",
                        "Систмный интегратор",
                    ),
                    accountingObjectList = listOf()
                ),
                InventoryCreateDomain(
                    number = "БП-00001376",
                    time = "12:40",
                    date = "12.12.12",
                    documentInfo = listOf(
                        "Систмный интегратор",
                        "Систмный интегратор",
                        "Систмный интегратор",
                        "Систмный интегратор",
                        "Систмный интегратор",
                    ),
                    accountingObjectList = listOf()
                ),
                InventoryCreateDomain(
                    number = "БП-00001377",
                    time = "12:40",
                    date = "12.12.12",
                    documentInfo = listOf(
                        "Систмный интегратор",
                        "Систмный интегратор",
                        "Систмный интегратор",
                        "Систмный интегратор",
                        "Систмный интегратор",
                    ),
                    accountingObjectList = listOf()
                )
            )
        ),
        AppInsets(topInset = previewTopInsetDp),
        {},
        {},
        {},
        {})
}
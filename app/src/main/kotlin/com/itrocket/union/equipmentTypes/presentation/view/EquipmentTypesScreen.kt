package com.itrocket.union.equipmentTypes.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.equipmentTypes.domain.entity.EquipmentTypesDomain
import com.itrocket.union.equipmentTypes.domain.entity.toDefaultItem
import com.itrocket.union.equipmentTypes.presentation.store.EquipmentTypeStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.DefaultListItem
import com.itrocket.union.ui.LoadingContent
import com.itrocket.union.ui.SearchToolbar

@Composable
fun EquipmentTypesScreen(
    state: EquipmentTypeStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onItemClickListener: (EquipmentTypesDomain) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchClickListener: () -> Unit,
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            SearchToolbar(
                title = stringResource(id = R.string.equipment_type_title),
                onBackClickListener = onBackClickListener,
                onSearchTextChanged = onSearchTextChanged,
                onSearchClickListener = onSearchClickListener,
                isShowSearch = state.isShowSearch,
                searchText = state.searchText
            )
            LoadingContent(isLoading = state.isLoading) {
                Content(
                    types = state.types,
                    onItemClickListener = onItemClickListener,
                    navBarPadding = appInsets.bottomInset
                )
            }
        }
    }
}

@Composable
private fun Content(
    types: List<EquipmentTypesDomain>,
    onItemClickListener: (EquipmentTypesDomain) -> Unit,
    navBarPadding: Int
) {
    LazyColumn {
        itemsIndexed(types, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = types.lastIndex != index
            DefaultListItem(
                item = item.toDefaultItem(),
                onItemClickListener = {
                    onItemClickListener(item)
                },
                isShowBottomLine = isShowBottomLine
            )
        }
        item {
            Spacer(modifier = Modifier.height(navBarPadding.dp))
        }
    }
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun EquipmentTypesScreenPreview() {
    EquipmentTypesScreen(
        EquipmentTypeStore.State(
        types = listOf(
            EquipmentTypesDomain(
                id = "1",
                catalogItemName = "name3",
                name = "name3",
                code = "code3"
            ),
            EquipmentTypesDomain(
                id = "2",
                catalogItemName = "name3",
                name = "name3",
                code = "code3"
            ),
            EquipmentTypesDomain(
                id = "3",
                catalogItemName = "name3",
                name = "name3",
                code = "code3"
            ),
        )
    ), AppInsets(), {}, {}, {}, {})
}
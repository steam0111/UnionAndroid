package com.itrocket.union.branches.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.branches.domain.entity.BranchesDomain
import com.itrocket.union.branches.domain.entity.toDefaultItem
import com.itrocket.union.branches.presentation.store.BranchesStore
import com.itrocket.union.common.DefaultItem
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.DefaultListItem
import com.itrocket.union.ui.LoadingContent
import com.itrocket.union.ui.SearchToolbar

@Composable
fun BranchesScreen(
    state: BranchesStore.State,
    appInsets: AppInsets,
    onBranchClickListener: (DefaultItem) -> Unit,
    onBackClickListener: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearchClickListener: () -> Unit,
    onFilterClickListener: () -> Unit
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            SearchToolbar(
                title = stringResource(id = R.string.branches_title),
                onBackClickListener = onBackClickListener,
                onSearchTextChanged = onSearchTextChanged,
                onSearchClickListener = onSearchClickListener,
                isShowSearch = state.isShowSearch,
                searchText = state.searchText,
                onFilterClickListener = onFilterClickListener
            )
            LoadingContent(isLoading = state.isLoading) {
                Content(
                    branches = state.branches,
                    onBranchClickListener = onBranchClickListener,
                    navBarPadding = appInsets.bottomInset
                )
            }
        }
    }
}

@Composable
private fun Content(
    branches: List<BranchesDomain>,
    onBranchClickListener: (DefaultItem) -> Unit,
    navBarPadding: Int
) {
    LazyColumn {
        itemsIndexed(branches, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = branches.lastIndex != index
            DefaultListItem(
                item = item.toDefaultItem(),
                onItemClickListener = onBranchClickListener,
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
fun BranchesScreenPreview() {
    BranchesScreen(
        BranchesStore.State(
            branches = listOf(
                BranchesDomain(
                    id = "0",
                    catalogItemName = "name0",
                    name = "name0",
                    code = "code0"
                ),
                BranchesDomain(
                    id = "1",
                    catalogItemName = "name1",
                    name = "name1",
                    code = "code1"
                ),
                BranchesDomain(
                    id = "2",
                    catalogItemName = "name2",
                    name = "name2",
                    code = "code2"
                ),
                BranchesDomain(
                    id = "3",
                    catalogItemName = "name3",
                    name = "name3",
                    code = "code3"
                )
            )
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {})
}
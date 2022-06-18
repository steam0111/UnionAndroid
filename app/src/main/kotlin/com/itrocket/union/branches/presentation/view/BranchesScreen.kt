package com.itrocket.union.branches.presentation.view

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.core.base.AppInsets
import com.itrocket.union.branches.presentation.store.BranchesStore
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.res.stringResource
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.branches.domain.entity.BranchesDomain
import com.itrocket.union.branches.domain.entity.toDefaultItem
import com.itrocket.union.common.DefaultItem
import com.itrocket.union.departments.domain.entity.DepartmentDomain
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.DefaultListItem
import com.itrocket.union.ui.LoadingContent

@Composable
fun BranchesScreen(
    state: BranchesStore.State,
    appInsets: AppInsets,
    onBranchClickListener: (DefaultItem) -> Unit,
    onBackClickListener: () -> Unit
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            BlackToolbar(
                title = stringResource(id = R.string.branches_title),
                onBackClickListener = onBackClickListener
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
        ), AppInsets(topInset = previewTopInsetDp), {}, {})
}
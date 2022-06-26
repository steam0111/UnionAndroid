package com.itrocket.union.departments.presentation.view

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
import com.itrocket.union.common.DefaultItem
import com.itrocket.union.departments.domain.entity.DepartmentDomain
import com.itrocket.union.departments.domain.entity.toDefaultItem
import com.itrocket.union.departments.presentation.store.DepartmentStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.DefaultListItem
import com.itrocket.union.ui.LoadingContent

@Composable
fun DepartmentScreen(
    state: DepartmentStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onItemClickListener: (DefaultItem) -> Unit,
    onFilterClickListener: () -> Unit
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            BlackToolbar(
                title = stringResource(id = R.string.departments_title),
                onBackClickListener = onBackClickListener,
                onFilterClickListener = onFilterClickListener
            )
            LoadingContent(isLoading = state.isLoading) {
                Content(
                    departments = state.departments,
                    navBarPadding = appInsets.bottomInset,
                    onItemClickListener = onItemClickListener
                )
            }
        }
    }
}

@Composable
private fun Content(
    departments: List<DepartmentDomain>,
    onItemClickListener: (DefaultItem) -> Unit,
    navBarPadding: Int
) {
    LazyColumn {
        itemsIndexed(departments, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = departments.lastIndex != index
            DefaultListItem(
                item = item.toDefaultItem(),
                onItemClickListener = onItemClickListener,
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
fun DepartmentScreenPreview() {
    DepartmentScreen(
        state = DepartmentStore.State(
            departments = listOf(
                DepartmentDomain(
                    id = "0",
                    catalogItemName = "name0",
                    name = "name0",
                    code = "code0"
                ),
                DepartmentDomain(
                    id = "1",
                    catalogItemName = "name1",
                    name = "name1",
                    code = "code1"
                ),
                DepartmentDomain(
                    id = "2",
                    catalogItemName = "name2",
                    name = "name2",
                    code = "code2"
                ),
                DepartmentDomain(
                    id = "3",
                    catalogItemName = "name3",
                    name = "name3",
                    code = "code3"
                )
            )
        ), appInsets = AppInsets(), onBackClickListener = {}, onItemClickListener = {}, {})
}
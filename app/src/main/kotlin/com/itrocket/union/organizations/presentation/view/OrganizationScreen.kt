package com.itrocket.union.organizations.presentation.view

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
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.common.DefaultItem
import com.itrocket.union.organizations.domain.entity.OrganizationDomain
import com.itrocket.union.organizations.domain.entity.toDefaultItem
import com.itrocket.union.organizations.presentation.store.OrganizationStore
import com.itrocket.union.ui.*

@Composable
fun OrganizationScreen(
    state: OrganizationStore.State,
    appInsets: AppInsets,
    onOrganizationClickListener: (DefaultItem) -> Unit,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit,
    onSearchTextChanged: (String) -> Unit
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            SearchToolbar(
                title = stringResource(id = R.string.organizations_title),
                onBackClickListener = onBackClickListener,
                onSearchTextChanged = onSearchTextChanged,
                onSearchClickListener = onSearchClickListener,
                isShowSearch = state.isShowSearch,
                searchText = state.searchText
            )
            LoadingContent(isLoading = state.isLoading) {
                Content(
                    organizations = state.organizations,
                    onOrganizationClickListener = onOrganizationClickListener,
                    navBarPadding = appInsets.bottomInset
                )
            }
        }
    }
}

@Composable
private fun Content(
    organizations: List<OrganizationDomain>,
    onOrganizationClickListener: (DefaultItem) -> Unit,
    navBarPadding: Int
) {
    LazyColumn {
        itemsIndexed(organizations, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = organizations.lastIndex != index
            DefaultListItem(
                item = item.toDefaultItem(),
                onItemClickListener = onOrganizationClickListener,
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
fun OrganizationsScreenPreview() {
    OrganizationScreen(
        OrganizationStore.State(
            organizations = listOf(
                OrganizationDomain(
                    id = "0",
                    name = "Organization0",
                    legalAddress = "here",
                    actualAddress = "there"
                ),
                OrganizationDomain(
                    id = "1",
                    name = "Organization1",
                    legalAddress = null,
                    actualAddress = null
                ),
                OrganizationDomain(
                    id = "2",
                    name = "Organization2",
                    legalAddress = null,
                    actualAddress = null
                ),
                OrganizationDomain(
                    id = "3",
                    name = "Organization3",
                    legalAddress = "here",
                    actualAddress = "there"
                )
            )
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {})
}
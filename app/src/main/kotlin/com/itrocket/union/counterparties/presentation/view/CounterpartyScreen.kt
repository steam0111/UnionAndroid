package com.itrocket.union.counterparties.presentation.view

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
import com.itrocket.union.counterparties.presentation.store.CounterpartyStore
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.res.stringResource
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.common.DefaultItem
import com.itrocket.union.counterparties.domain.entity.CounterpartyDomain
import com.itrocket.union.counterparties.domain.entity.toDefaultItem
import com.itrocket.union.organizations.domain.entity.OrganizationDomain
import com.itrocket.union.organizations.domain.entity.toDefaultItem
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.DefaultListItem
import com.itrocket.union.ui.LoadingContent
import com.itrocket.union.ui.SearchToolbar

@Composable
fun CounterpartyScreen(
    state: CounterpartyStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onCounterpartyClickListener: (CounterpartyDomain) -> Unit,
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
                title = stringResource(id = R.string.counterparties_title),
                onBackClickListener = onBackClickListener,
                onSearchClickListener = onSearchClickListener,
                onSearchTextChanged = onSearchTextChanged,
                isShowSearch = state.isShowSearch,
                searchText = state.searchText
            )
            LoadingContent(isLoading = state.isLoading) {
                Content(
                    counterparties = state.counterparties,
                    onCounterpartyClickListener = onCounterpartyClickListener,
                    navBarPadding = appInsets.bottomInset
                )
            }
        }
    }
}

@Composable
private fun Content(
    counterparties: List<CounterpartyDomain>,
    onCounterpartyClickListener: (CounterpartyDomain) -> Unit,
    navBarPadding: Int
) {
    LazyColumn {
        itemsIndexed(counterparties, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = counterparties.lastIndex != index
            DefaultListItem(
                item = item.toDefaultItem(),
                onItemClickListener = {
                    onCounterpartyClickListener(item)
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
fun CounterpartyScreenPreview() {
    CounterpartyScreen(CounterpartyStore.State(
        counterparties = listOf(
            CounterpartyDomain(
                id = "1",
                catalogItemName = "blb",
                name = "fsdfs",
                actualAddress = "ffsdf",
                legalAddress = "fsdfsd",
                inn = "f123",
                kpp = "345345"
            ),
            CounterpartyDomain(
                id = "2",
                catalogItemName = "blb",
                name = "fsdfs",
                actualAddress = "ffsdf",
                legalAddress = "fsdfsd",
                inn = "f123",
                kpp = "345345"
            ),
            CounterpartyDomain(
                id = "3",
                catalogItemName = "blb",
                name = "fsdfs",
                actualAddress = "ffsdf",
                legalAddress = "fsdfsd",
                inn = "f123",
                kpp = "345345"
            ),
        )
    ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {})
}
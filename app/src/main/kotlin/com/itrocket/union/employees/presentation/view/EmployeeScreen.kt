package com.itrocket.union.employees.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
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
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import com.itrocket.union.employees.presentation.store.EmployeeStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.EmployeeItem
import com.itrocket.union.ui.LoadingContent
import com.itrocket.union.ui.SearchToolbar
import com.itrocket.utils.paging.subscribePagingListIndex

@Composable
fun EmployeesScreen(
    state: EmployeeStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit = {},
    onFilterClickListener: () -> Unit = {},
    onEmployeeClickListener: (EmployeeDomain) -> Unit = {},
    onSearchTextChanged: (String) -> Unit,
    onLoadNext: () -> Unit
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            SearchToolbar(
                title = stringResource(id = R.string.employees_title),
                onBackClickListener = onBackClickListener,
                onSearchClickListener = onSearchClickListener,
                onFilterClickListener = onFilterClickListener,
                onSearchTextChanged = onSearchTextChanged,
                isShowSearch = state.isShowSearch,
                searchText = state.searchText,
            )
            EmployeesList(
                employees = state.employees,
                navBarPadding = appInsets.bottomInset,
                onEmployeeClickListener = {
                    onEmployeeClickListener(it)
                },
                onLoadNext = onLoadNext,
                isLoading = state.isLoading,
                isEndReached = state.isListEndReached
            )
        }
    }
}

@Composable
private fun EmployeesList(
    employees: List<EmployeeDomain>,
    navBarPadding: Int,
    onEmployeeClickListener: (EmployeeDomain) -> Unit,
    isLoading: Boolean,
    isEndReached: Boolean,
    onLoadNext: () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        itemsIndexed(employees, key = { index, item ->
            item.id
        }) { index, item ->
            val isShowBottomLine = employees.lastIndex != index
            EmployeeItem(
                item = item,
                onEmployeeClickListener = onEmployeeClickListener,
                isShowBottomLine = isShowBottomLine
            )
        }
        item {
            Spacer(modifier = Modifier.height(navBarPadding.dp))
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
            listSize = employees.size,
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
fun EmployeeScreenPreview() {
    EmployeesScreen(
        EmployeeStore.State(
            employees = listOf(
                EmployeeDomain(
                    id = "1",
                    firstname = "Ли",
                    lastname = "Мин",
                    patronymic = "Хо",
                    catalogItemName = "Ли Мин Хо",
                    number = "12345667890",
                    nfc = "dbdj",
                    post = "dfdf",
                ),
                EmployeeDomain(
                    id = "2",
                    firstname = "Ким",
                    lastname = "У",
                    patronymic = "Бин",
                    catalogItemName = "Ким У Бин",
                    number = "0987654321",
                    nfc = null,
                    post = "dfdf",
                )
            )
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {}, {})
}
package com.itrocket.union.employees.presentation.view

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
import com.itrocket.union.employees.domain.entity.EmployeeDomain
import com.itrocket.union.employees.domain.entity.EmployeeStatus
import com.itrocket.union.employees.presentation.store.EmployeeStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BlackToolbar
import com.itrocket.union.ui.EmployeeItem
import com.itrocket.union.ui.LoadingContent

@Composable
fun EmployeesScreen(
    state: EmployeeStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit = {},
    onFilterClickListener: () -> Unit = {},
    onEmployeeClickListener: (EmployeeDomain) -> Unit = {}
) {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp)
        ) {
            BlackToolbar(
                title = stringResource(id = R.string.employees_title),
                onBackClickListener = onBackClickListener,
                onSearchClickListener = onSearchClickListener,
                onFilterClickListener = onFilterClickListener
            )
            LoadingContent(isLoading = state.isLoading) {
                EmployeesList(
                    employees = state.employees,
                    navBarPadding = appInsets.bottomInset,
                    onEmployeeClickListener = {
                        onEmployeeClickListener(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun EmployeesList(
    employees: List<EmployeeDomain>,
    navBarPadding: Int,
    onEmployeeClickListener: (EmployeeDomain) -> Unit
) {
    LazyColumn {
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
                    employeeStatus = EmployeeStatus.MOL
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
                    employeeStatus = EmployeeStatus.WITHOUT_ORGANIZATION
                )
            )
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {})
}
package com.itrocket.union.filter.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.domain.entity.FilterValueType
import com.itrocket.union.filter.presentation.store.FilterStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.SelectedBaseField
import com.itrocket.union.ui.UnselectedBaseField
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.psb6
import com.itrocket.union.ui.white

@Composable
fun FilterScreen(
    state: FilterStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    onFieldClickListener: (FilterDomain) -> Unit,
    onShowClickListener: () -> Unit
) {
    val resources = LocalContext.current.resources
    AppTheme {
        Scaffold(
            topBar = {
                FilterToolbar(
                    onCrossClickListener = onBackClickListener,
                    onDropClickListener = onDropClickListener
                )
            },
            content = {
                FilterContent(
                    filters = state.filterFields,
                    onFieldClickListener = onFieldClickListener
                )
            },
            bottomBar = {
                FilterBottomBar(
                    resultCount = state.resultCount,
                    onBtnClickListener = onShowClickListener,
                    resources = resources
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        )
    }
}

@Composable
private fun FilterToolbar(onCrossClickListener: () -> Unit, onDropClickListener: () -> Unit) {
    BaseToolbar(
        title = stringResource(R.string.filter_title),
        textColor = white,
        startImageId = R.drawable.ic_cross,
        onStartImageClickListener = onCrossClickListener,
        backgroundColor = psb1
    ) {
        Text(
            text = stringResource(id = R.string.common_drop),
            color = psb6,
            modifier = Modifier.clickable(onClick = onDropClickListener)
        )
    }
}

@Composable
private fun FilterContent(
    filters: List<FilterDomain>,
    onFieldClickListener: (FilterDomain) -> Unit
) {
    LazyColumn {
        items(filters) {
            if (it.values.isEmpty()) {
                UnselectedBaseField(label = it.name, onFieldClickListener = {
                    onFieldClickListener(it)
                })
            } else {
                SelectedBaseField(
                    label = it.name,
                    value = it.values.joinToString(", "),
                    onFieldClickListener = {
                        onFieldClickListener(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun FilterBottomBar(
    resultCount: Int,
    onBtnClickListener: () -> Unit,
    resources: Resources
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        BaseButton(
            text = resources.getQuantityString(
                R.plurals.filter_result_show,
                resultCount,
                resultCount
            ),
            onClick = onBtnClickListener,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun FilterScreenPreview() {
    FilterScreen(
        FilterStore.State(
            filterFields = listOf(
                FilterDomain(
                    name = "Местоположение",
                    filterValueType = FilterValueType.SINGLE_SELECT_LIST
                ),
                FilterDomain(name = "Статус", filterValueType = FilterValueType.SINGLE_SELECT_LIST),
                FilterDomain(
                    name = "Дата заявки",
                    values = listOf("За все время"),
                    valueList = listOf(
                        "За все время",
                        "За сегодня",
                        "За 2 дня",
                        "За 3 дня",
                        "За неделю"
                    ),
                    filterValueType = FilterValueType.SINGLE_SELECT_LIST,
                )
            )
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {})
}
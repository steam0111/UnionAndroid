package com.itrocket.union.filterValues.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.ui.EditText
import com.itrocket.union.R
import com.itrocket.union.filter.domain.entity.FilterDomain
import com.itrocket.union.filter.domain.entity.FilterValueType
import com.itrocket.union.filterValues.presentation.store.FilterValueStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseCheckbox
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.RadioButtonField
import com.itrocket.union.ui.TextButton
import com.itrocket.union.ui.blue6
import com.itrocket.union.ui.brightGray
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.psb6
import com.itrocket.union.ui.white
import com.itrocket.utils.rememberViewInteropNestedScrollConnection

@ExperimentalPagerApi
@Composable
fun FilterValueScreen(
    state: FilterValueStore.State,
    appInsets: AppInsets,
    onDropClickListener: () -> Unit,
    onApplyClickListener: () -> Unit,
    onValueSelected: (String) -> Unit,
    onCrossClickListener: () -> Unit,
    onSingleValueChanged: (String) -> Unit,
    onCloseClickListener: () -> Unit
) {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = appInsets.bottomInset.dp, top = appInsets.topInset.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(white, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(top = 16.dp),
            ) {
                FilterValueTopBar(
                    title = state.filter.name,
                    onCrossClickListener = onCrossClickListener
                )
                FilterValueContent(
                    filterValues = state.filter.valueList,
                    onValueSelected = onValueSelected,
                    paddingValues = PaddingValues(
                        bottom = 88.dp,
                        top = 16.dp
                    ),
                    selectedFilterValues = state.filterValues,
                    filterValueType = state.filter.filterValueType,
                    singleValue = state.singleValue,
                    onSingleValueChanged = onSingleValueChanged
                )
            }
            FilterValueBottomBar(
                filterValueType = state.filter.filterValueType,
                onApplyClickListener = onApplyClickListener,
                onDropClickListener = onDropClickListener,
                onCloseClickListener = onCloseClickListener,
                isShowDrop = state.filterValues.isNotEmpty()
            )
        }
    }
}

@Composable
private fun FilterValueTopBar(title: String, onCrossClickListener: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = AppTheme.typography.h6,
            color = psb1
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_cross),
            contentDescription = null,
            modifier = Modifier.clickable(
                onClick = onCrossClickListener, interactionSource = remember {
                    MutableInteractionSource()
                }, indication = rememberRipple(bounded = false)
            ),
            colorFilter = ColorFilter.tint(psb6)
        )
    }
}

@ExperimentalPagerApi
@Composable
private fun FilterValueContent(
    filterValues: List<String>,
    singleValue: String,
    selectedFilterValues: List<String>,
    onValueSelected: (String) -> Unit,
    onSingleValueChanged: (String) -> Unit,
    filterValueType: FilterValueType,
    paddingValues: PaddingValues
) {
    Surface(
        // Добавляет возможность скроллить внутренний компонент без скролла внешнего.
        // Когда скролл внутреннего компонента дошел до конца,
        // начинается скролл внешнего компонента
        modifier = Modifier.nestedScroll(rememberViewInteropNestedScrollConnection())
    ) {
        when (filterValueType) {
            FilterValueType.INPUT -> {
                FilterValueInput(
                    inputText = singleValue,
                    onInputTextChanged = onSingleValueChanged,
                    paddingValues = paddingValues
                )
            }
            FilterValueType.MULTI_SELECT_LIST, FilterValueType.SINGLE_SELECT_LIST -> {
                FilterValueList(
                    paddingValues = paddingValues,
                    filterValues = filterValues,
                    filterValueType = filterValueType,
                    onValueSelected = onValueSelected,
                    selectedFilterValues = selectedFilterValues
                )
            }
        }
    }
}

@Composable
private fun FilterValueInput(
    inputText: String,
    onInputTextChanged: (String) -> Unit,
    paddingValues: PaddingValues
) {
    var underlineColor by remember {
        mutableStateOf(brightGray)
    }
    val focusRequest = remember {
        FocusRequester()
    }
    Column(modifier = Modifier.padding(paddingValues)) {
        MediumSpacer()
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 4.dp),
            text = inputText,
            hint = stringResource(R.string.common_hint),
            textStyle = AppTheme.typography.body1,
            hintStyle = AppTheme.typography.body2,
            hintColor = graphite4,
            onTextChanged = onInputTextChanged,
            focusRequester = focusRequest,
            onFocusChanged = {
                underlineColor = if (it.hasFocus) {
                    blue6
                } else {
                    brightGray
                }
            },
            underlineColor = underlineColor
        )
        MediumSpacer()
    }
}

@Composable
private fun FilterValueList(
    paddingValues: PaddingValues,
    filterValues: List<String>,
    filterValueType: FilterValueType,
    onValueSelected: (String) -> Unit,
    selectedFilterValues: List<String>,
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        itemsIndexed(filterValues) { index, item ->
            val isShowBottomLine = index != filterValues.lastIndex
            if (filterValueType == FilterValueType.MULTI_SELECT_LIST) {
                CheckableField(
                    label = item,
                    onFieldClickListener = {
                        onValueSelected(item)
                    },
                    isChecked = selectedFilterValues.contains(item),
                    isShowBottomLine = isShowBottomLine
                )
            } else {
                RadioButtonField(
                    label = item,
                    onFieldClickListener = { onValueSelected(item) },
                    isSelected = selectedFilterValues.contains(item),
                    isShowBottomLine = isShowBottomLine
                )
            }
        }
    }
}

@Composable
fun CheckableField(
    isShowBottomLine: Boolean = true,
    label: String,
    onFieldClickListener: () -> Unit,
    isChecked: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onFieldClickListener()
            }
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, style = AppTheme.typography.body2, color = psb1)
            Spacer(modifier = Modifier.weight(1f))
            BaseCheckbox(
                isChecked = isChecked, onCheckClickListener = {
                    onFieldClickListener()
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (isShowBottomLine) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(brightGray)
            )
        }
    }
}

@Composable
private fun FilterValueBottomBar(
    filterValueType: FilterValueType,
    isShowDrop: Boolean,
    onApplyClickListener: () -> Unit,
    onDropClickListener: () -> Unit,
    onCloseClickListener: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(white),
        horizontalArrangement = Arrangement.End
    ) {
        if (filterValueType == FilterValueType.MULTI_SELECT_LIST && isShowDrop) {
            TextButton(
                text = stringResource(R.string.common_drop).uppercase(),
                onClick = onDropClickListener
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        if (filterValueType == FilterValueType.INPUT) {
            TextButton(
                text = stringResource(R.string.common_cancel),
                onClick = onCloseClickListener
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        TextButton(
            text = stringResource(R.string.common_apply),
            onClick = onApplyClickListener
        )
    }
}

@ExperimentalPagerApi
@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun FilterValueScreenPreview(
    filter: FilterDomain = FilterDomain(
        name = "Filter name",
        filterValueType = FilterValueType.MULTI_SELECT_LIST,
        valueList = listOf("Value1", "Value2", "Value3")
    )
) {
    FilterValueScreen(
        FilterValueStore.State(
            filter = filter,
            filterValues = listOf("Value1")
        ),
        AppInsets(topInset = previewTopInsetDp),
        {},
        {},
        {},
        {},
        {},
        {}
    )
}
package com.itrocket.union.selectParams.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.ui.EditText
import com.itrocket.union.R
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.selectParams.domain.SelectParamsInteractor.Companion.MIN_CURRENT_STEP
import com.itrocket.union.selectParams.presentation.store.SelectParamsStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.ButtonWithContent
import com.itrocket.union.ui.IndicatorWithText
import com.itrocket.union.ui.LoadingContent
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.OutlinedImageButton
import com.itrocket.union.ui.RadioButtonField
import com.itrocket.union.ui.SelectedBaseField
import com.itrocket.union.ui.UnselectedBaseField
import com.itrocket.union.ui.brightGray
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.psb3
import com.itrocket.union.ui.psb6
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded

@Composable
fun SelectParamsScreen(
    state: SelectParamsStore.State,
    appInsets: AppInsets,
    onCrossClickListener: () -> Unit,
    onAcceptClickListener: () -> Unit,
    onNextClickListener: () -> Unit,
    onPrevClickListener: () -> Unit,
    onSearchTextChanged: (TextFieldValue) -> Unit,
    onItemSelected: (String) -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    onAcceptClickListener = onAcceptClickListener,
                    onCrossClickListener = onCrossClickListener
                )
            },
            bottomBar = {
                BottomBar(
                    onNextClickListener = onNextClickListener,
                    onPrevClickListener = onPrevClickListener,
                    currentStep = state.currentStep,
                    stepCount = state.params.size
                )
            },
            content = {
                LoadingContent(isLoading = state.isLoading) {
                    Content(
                        currentParam = state.params[state.currentStep - 1],
                        currentParamValues = state.currentParamValues,
                        paddingValues = it,
                        onItemSelected = onItemSelected,
                        onSearchTextChanged = onSearchTextChanged,
                        searchText = state.searchText
                    )
                }
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        )
    }
}

@Composable
private fun TopBar(
    onCrossClickListener: () -> Unit,
    onAcceptClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(psb1)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_cross),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(onClick = onCrossClickListener)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_accept),
            contentDescription = null,
            modifier = Modifier.clickableUnbounded(onClick = onAcceptClickListener)
        )
    }
}

@Composable
private fun Content(
    currentParam: ParamDomain,
    currentParamValues: List<String>,
    paddingValues: PaddingValues,
    searchText: TextFieldValue,
    onSearchTextChanged: (TextFieldValue) -> Unit,
    onItemSelected: (String) -> Unit
) {
    var underlineColor by remember {
        mutableStateOf(brightGray)
    }
    val focusRequester = FocusRequester()
    Column(modifier = Modifier.padding(paddingValues)) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(white)
                .padding(vertical = 4.dp)
        ) {
            EditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(white)
                    .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 4.dp),
                textField = searchText,
                hint = stringResource(currentParam.type.titleId),
                hintStyle = AppTheme.typography.body1,
                hintColor = psb3,
                textStyle = AppTheme.typography.body1,
                onTextChanged = onSearchTextChanged,
                focusRequester = focusRequester,
                onFocusChanged = {
                    underlineColor = if (it.hasFocus) {
                        psb6
                    } else {
                        brightGray
                    }
                },
                underlineColor = underlineColor
            )
        }
        MediumSpacer()
        LazyColumn {
            itemsIndexed(currentParamValues) { index, item ->
                val isShowBottomLine = index != currentParamValues.lastIndex
                RadioButtonField(
                    label = item,
                    onFieldClickListener = { onItemSelected(item) },
                    isSelected = item == currentParam.value,
                    isShowBottomLine = isShowBottomLine
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    onNextClickListener: () -> Unit,
    onPrevClickListener: () -> Unit,
    currentStep: Int,
    stepCount: Int

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IndicatorWithText(
            step = currentStep,
            stepCount = stepCount,
            text = stringResource(
                id = R.string.common_step,
                currentStep.toString()
            ),
            modifier = Modifier.size(90.dp, 4.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedImageButton(
            imageId = R.drawable.ic_arrow_back,
            onClick = onPrevClickListener,
            enabled = currentStep > MIN_CURRENT_STEP,
            modifier = Modifier,
            paddingValues = PaddingValues(16.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        ButtonWithContent(
            content = {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentStep == stepCount) {
                        Text(
                            text = stringResource(id = R.string.common_finish).uppercase(),
                            style = AppTheme.typography.body2,
                            color = white
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.common_next).uppercase(),
                            style = AppTheme.typography.body2,
                            color = white
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = null
                        )
                    }
                }
            },
            onClick = onNextClickListener,
            isEnabled = true,
            modifier = Modifier
        )
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
fun SelectParamsScreenPreview() {
    SelectParamsScreen(
        SelectParamsStore.State(
            currentStep = 1,
            params = listOf(
                ParamDomain("param", ManualType.ORGANIZATION),
                ParamDomain("param", ManualType.MOL),
                ParamDomain("param", ManualType.LOCATION)
            )
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {}, {}, {})
}
package com.itrocket.union.structural.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.EditText
import com.itrocket.union.R
import com.itrocket.union.structural.domain.entity.StructuralDomain
import com.itrocket.union.structural.presentation.store.StructuralStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.RadioButtonField
import com.itrocket.union.ui.brightGray
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.psb3
import com.itrocket.union.ui.psb4
import com.itrocket.union.ui.psb6
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded

@Composable
fun StructuralScreen(
    state: StructuralStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onCrossClickListener: () -> Unit,
    onAcceptClickListener: () -> Unit,
    onFinishClickListener: () -> Unit,
    onStructuralSelected: (StructuralDomain) -> Unit,
    onSearchTextChanged: (String) -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                BaseToolbar(
                    title = stringResource(id = R.string.manual_structural),
                    startImageId = R.drawable.ic_cross,
                    onStartImageClickListener = onCrossClickListener,
                    backgroundColor = white,
                    textColor = psb1,
                    content = {
                        if (state.isCanEdit) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_accept),
                                    contentDescription = null,
                                    modifier = Modifier.clickableUnbounded(onClick = onAcceptClickListener)
                                )
                            }
                        }
                    }
                )
            }, bottomBar = {
                if (state.isCanEdit) {
                    BottomBar(onFinishClickListener = onFinishClickListener)
                }
            }, content = {
                Content(
                    state = state,
                    onBackClickListener = onBackClickListener,
                    onStructuralSelected = onStructuralSelected,
                    paddingValues = it,
                    onSearchTextChanged = onSearchTextChanged
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
private fun Content(
    state: StructuralStore.State,
    onBackClickListener: () -> Unit,
    onStructuralSelected: (StructuralDomain) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    paddingValues: PaddingValues
) {
    var underlineColor by remember {
        mutableStateOf(graphite2)
    }
    val focusRequest = remember {
        FocusRequester()
    }
    Column(Modifier.padding(paddingValues = paddingValues)) {
        StructuralComponent(
            selectedStructuralScheme = state.selectStructuralScheme,
            onBackClickListener = onBackClickListener,
            isLevelHintShow = state.isLevelHintShow
        )
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 4.dp),
            text = state.searchText,
            hint = stringResource(id = R.string.structural_hint),
            textStyle = AppTheme.typography.body1,
            hintStyle = AppTheme.typography.body2,
            hintColor = psb3,
            onTextChanged = onSearchTextChanged,
            focusRequester = focusRequest,
            onFocusChanged = {
                underlineColor = if (it.hasFocus) {
                    psb6
                } else {
                    brightGray
                }
            },
            underlineColor = underlineColor
        )
        MediumSpacer()
        LazyColumn {
            items(state.structuralValues) {
                RadioButtonField(
                    label = it.value,
                    onFieldClickListener = {
                        onStructuralSelected(it)
                    },
                    isSelected = state.selectStructuralScheme.contains(it),
                )
            }
        }
    }
}

@Composable
private fun BottomBar(onFinishClickListener: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(16.dp)
    ) {
        BaseButton(
            text = stringResource(R.string.common_finish),
            onClick = onFinishClickListener,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun StructuralComponent(
    selectedStructuralScheme: List<StructuralDomain>,
    isLevelHintShow: Boolean,
    onBackClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(psb1)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ArrowBackButton(
            enabled = selectedStructuralScheme.isNotEmpty(),
            onClick = onBackClickListener
        )
        Spacer(modifier = Modifier.width(24.dp))
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.structural_level),
                style = AppTheme.typography.body2,
                color = psb4
            )
            Spacer(modifier = Modifier.height(4.dp))
            StructuralLevelComponent(selectedStructuralScheme, isLevelHintShow)
        }
    }
}

@Composable
private fun StructuralLevelComponent(
    selectedStructuralScheme: List<StructuralDomain>,
    isLevelHintShow: Boolean
) {
    val annotatedString = buildAnnotatedString {
        selectedStructuralScheme.forEachIndexed { index, locationDomain ->
            val itemId = "item$index"
            val placeholderId = "[icon$index]"
            append(locationDomain.value)
            if (isLevelHintShow || index < selectedStructuralScheme.lastIndex) {
                appendInlineContent(itemId, placeholderId)
            }
        }
        if (isLevelHintShow) {
            withStyle(SpanStyle(color = psb6, fontSize = 14.sp, fontWeight = FontWeight.Normal)) {
                append(stringResource(id = R.string.structural_select_structural))
            }
        }
    }
    val inlineContent = mutableMapOf<String, InlineTextContent>()
    selectedStructuralScheme.forEachIndexed { index, _ ->
        val itemId = "item$index"
        val textInlineContent = InlineTextContent(
            Placeholder(
                width = 32.sp,
                height = 12.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_arrow_right_small),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        inlineContent[itemId] = textInlineContent
    }
    Text(
        text = annotatedString,
        inlineContent = inlineContent,
        color = white,
        style = AppTheme.typography.body2,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp
    )
}

@Composable
private fun ArrowBackButton(enabled: Boolean, onClick: () -> Unit) {
    Box(
        Modifier
            .border(
                width = 1.dp,
                color = if (enabled) {
                    psb6
                } else {
                    psb3
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                if (enabled) {
                    psb6
                } else {
                    psb3
                }
            )
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
fun StructuralScreenPreview() {
    StructuralScreen(StructuralStore.State(isCanEdit = true), AppInsets(), {}, {}, {}, {}, {}, {})
}
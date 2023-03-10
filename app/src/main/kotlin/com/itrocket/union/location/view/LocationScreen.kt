package com.itrocket.union.location.presentation.view

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
import com.itrocket.ui.EditText
import com.itrocket.union.R
import com.itrocket.union.location.domain.entity.LocationDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.selectParams.presentation.store.SelectParamsStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.RadioButtonField
import com.itrocket.union.ui.brightGray
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.psb4
import com.itrocket.union.ui.white

@Composable
fun LocationContent(
    state: SelectParamsStore.State,
    onBackClickListener: () -> Unit,
    onPlaceSelected: (LocationDomain) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    paddingValues: PaddingValues
) {
    var underlineColor by remember {
        mutableStateOf(graphite2)
    }
    val focusRequest = remember {
        FocusRequester()
    }
    val mainColor = AppTheme.colors.mainColor
    Column(Modifier.padding(paddingValues = paddingValues)) {
        PlaceComponent(
            selectedPlaceScheme = (state.currentParam as LocationParamDomain).locations,
            onBackClickListener = onBackClickListener,
            levelHint = state.levelHint
        )
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 4.dp),
            text = state.searchText,
            hint = stringResource(state.currentParam.type.titleId),
            textStyle = AppTheme.typography.body1,
            hintStyle = AppTheme.typography.body2,
            hintColor = AppTheme.colors.secondaryColor,
            onTextChanged = onSearchTextChanged,
            focusRequester = focusRequest,
            onFocusChanged = {
                underlineColor = if (it.hasFocus) {
                    mainColor
                } else {
                    brightGray
                }
            },
            underlineColor = underlineColor
        )
        MediumSpacer()
        LazyColumn {
            items(state.locationValues) {
                RadioButtonField(
                    label = it.value,
                    onFieldClickListener = {
                        onPlaceSelected(it)
                    },
                    isSelected = state.currentParam.locations.contains(it),
                )
            }
        }
    }
}

@Composable
private fun PlaceComponent(
    selectedPlaceScheme: List<LocationDomain>,
    levelHint: String,
    onBackClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.appBarBackgroundColor)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ArrowBackButton(
            enabled = selectedPlaceScheme.isNotEmpty(),
            onClick = onBackClickListener
        )
        Spacer(modifier = Modifier.width(24.dp))
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.location_level),
                style = AppTheme.typography.body2,
                color = psb4
            )
            Spacer(modifier = Modifier.height(4.dp))
            LocationLevelComponent(selectedPlaceScheme, levelHint)
        }
    }
}

@Composable
private fun LocationLevelComponent(selectedPlaceScheme: List<LocationDomain>, levelHint: String) {
    val annotatedString = buildAnnotatedString {
        selectedPlaceScheme.forEachIndexed { index, locationDomain ->
            val itemId = "item$index"
            val placeholderId = "[icon$index]"
            append(locationDomain.value)
            if (levelHint.isNotBlank() || index < selectedPlaceScheme.lastIndex) {
                appendInlineContent(itemId, placeholderId)
            }
        }
        if (levelHint.isNotBlank()) {
            withStyle(
                SpanStyle(
                    color = AppTheme.colors.mainColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(stringResource(id = R.string.location_select_place, levelHint.lowercase()))
            }
        }
    }
    val inlineContent = mutableMapOf<String, InlineTextContent>()
    selectedPlaceScheme.forEachIndexed { index, _ ->
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
                    AppTheme.colors.mainColor
                } else {
                    AppTheme.colors.secondaryColor
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
                    AppTheme.colors.mainColor
                } else {
                    AppTheme.colors.secondaryColor
                }
            )
        )
    }
}

@Preview(
    name = "?????????????? ???????? ?????????? - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "???????????? ???????? ?????????? - 4,95 (1920 ?? 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(name = "??????????????", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun LocationScreenPreview() {
    LocationContent(SelectParamsStore.State(
        locationValues = listOf(
            LocationDomain("1", "1", "????????????", "A"),
            LocationDomain("1", "1", "????????????", "??"),
            LocationDomain("1", "1", "????????????", "??"),
            LocationDomain("1", "1", "????????????", "D")
        ),
        levelHint = "????????????",
        allParams = listOf(),
        currentParam = LocationParamDomain(),
        currentStep = 0
    ), {}, {}, {}, PaddingValues()
    )
}
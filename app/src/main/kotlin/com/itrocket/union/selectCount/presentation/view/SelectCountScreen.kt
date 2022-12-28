package com.itrocket.union.selectCount.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.EditText
import com.itrocket.union.R
import com.itrocket.union.selectCount.presentation.store.SelectCountStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BottomSheetDivider
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.brightGray
import com.itrocket.union.ui.white
import java.math.BigDecimal

@Composable
fun SelectCountScreen(
    state: SelectCountStore.State,
    appInsets: AppInsets,
    onAcceptClickListener: () -> Unit,
    onCountChanged: (String) -> Unit
) {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = appInsets.bottomInset.dp,
                    top = appInsets.topInset.dp
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(white, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSheetDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.select_count_title),
                    style = AppTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(16.dp))
                MediumSpacer()
                var underlineColor by remember {
                    mutableStateOf(brightGray)
                }
                val focusRequester = FocusRequester()
                val mainColor = AppTheme.colors.mainColor
                val countString = if (state.count == BigDecimal.ZERO) {
                    ""
                } else {
                    state.count.toString()
                }
                EditText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(white)
                        .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 4.dp),
                    text = countString,
                    hint = stringResource(R.string.count),
                    hintStyle = AppTheme.typography.body1,
                    hintColor = AppTheme.colors.secondaryColor,
                    textStyle = AppTheme.typography.body1,
                    onTextChanged = {
                        if (it.isDigitsOnly()) {
                            onCountChanged(it)
                        }
                    },
                    focusRequester = focusRequester,
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
                Spacer(modifier = Modifier.height(16.dp))
                BaseButton(
                    text = stringResource(R.string.common_apply),
                    onClick = onAcceptClickListener,
                    enabled = state.count != BigDecimal.ZERO,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
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
fun SelectCountScreenPreview() {
    SelectCountScreen(SelectCountStore.State(
        id = "",
        count = BigDecimal(123)
    ), AppInsets(), {}, {})
}
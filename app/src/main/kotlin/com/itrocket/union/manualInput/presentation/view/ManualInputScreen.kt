package com.itrocket.union.manualInput.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.core.base.AppInsets
import com.itrocket.union.manualInput.presentation.store.ManualInputStore
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.itrocket.ui.EditText
import com.itrocket.union.manualInput.presentation.store.ManualInputType
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.ButtonBottomBar
import com.itrocket.union.ui.brightGray
import com.itrocket.union.ui.psb4
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded

@Composable
fun ManualInputScreen(
    state: ManualInputStore.State,
    appInsets: AppInsets,
    onCloseClickListener: () -> Unit,
    onAcceptClickListener: () -> Unit,
    onTextChanged: (String) -> Unit
) {
    AppTheme {
        var underlineColor by remember {
            mutableStateOf(brightGray)
        }
        val mainColor = AppTheme.colors.mainColor
        val focusRequester = FocusRequester()
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
                    .padding(
                        top = 16.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.manual_input_title),
                        style = AppTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_cross),
                        contentDescription = null,
                        modifier = Modifier.clickableUnbounded(onClick = onCloseClickListener),
                        colorFilter = ColorFilter.tint(AppTheme.colors.mainColor)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(psb4)
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(white)
                        .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 4.dp),
                    text = state.text,
                    hint = stringResource(state.type.hintId),
                    onTextChanged = onTextChanged,
                    focusRequester = focusRequester,
                    hintStyle = AppTheme.typography.body1,
                    hintColor = AppTheme.colors.secondaryColor,
                    textStyle = AppTheme.typography.body1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onFocusChanged = {
                        underlineColor = if (it.hasFocus) {
                            mainColor
                        } else {
                            brightGray
                        }
                    },
                    underlineColor = underlineColor
                )
                Box(modifier = Modifier.padding(16.dp)) {
                    BaseButton(
                        text = stringResource(R.string.common_apply),
                        onClick = onAcceptClickListener,
                        modifier = Modifier.fillMaxWidth(),
                        isAllUppercase = false
                    )
                }
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
fun ManualInputScreenPreview() {
    ManualInputScreen(
        ManualInputStore.State(type = ManualInputType.BARCODE),
        AppInsets(),
        {},
        {},
        {})
}
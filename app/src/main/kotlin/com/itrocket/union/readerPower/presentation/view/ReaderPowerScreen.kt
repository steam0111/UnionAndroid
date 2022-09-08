package com.itrocket.union.readerPower.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.ui.EditText
import com.itrocket.union.R
import com.itrocket.union.readerPower.domain.ReaderPowerInteractor.Companion.MIN_READER_POWER
import com.itrocket.union.readerPower.presentation.store.ReaderPowerStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.MediumSpacer
import com.itrocket.union.ui.ReaderPowerPicker
import com.itrocket.union.ui.TextButton
import com.itrocket.union.ui.brightGray
import com.itrocket.union.ui.graphite1
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.white

@Composable
fun ReaderPowerScreen(
    state: ReaderPowerStore.State,
    appInsets: AppInsets,
    onCrossClickListener: () -> Unit,
    onCancelClickListener: () -> Unit,
    onAcceptClickListener: () -> Unit,
    onPowerChanged: (String?) -> Unit,
    onArrowUpClickListener: () -> Unit,
    onArrowDownClickListener: () -> Unit
) {
    var underlineColor by remember {
        mutableStateOf(brightGray)
    }
    val mainColor = AppTheme.colors.mainColor
    val focusRequester = FocusRequester()
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
                TopBar(onCrossClickListener = onCrossClickListener)
                MediumSpacer()
                EditText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(white)
                        .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 4.dp),
                    text = if (state.readerPower != null) state.readerPower.toString() else "",
                    hint = stringResource(R.string.reader_power_hint),
                    onTextChanged = onPowerChanged,
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
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(graphite1)
                )
                ReaderPowerPicker(
                    power = state.readerPower ?: MIN_READER_POWER,
                    onPowerChanged = onPowerChanged,
                    onArrowDownClickListener = onArrowDownClickListener,
                    onArrowUpClickListener = onArrowUpClickListener
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
            BottomBar(
                onCancelClickListener = onCancelClickListener,
                onAcceptClickListener = onAcceptClickListener
            )
        }
    }
}

@Composable
private fun BottomBar(onCancelClickListener: () -> Unit, onAcceptClickListener: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(16.dp)
            .padding(top = 4.dp)
            .background(white)
            .padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            text = stringResource(R.string.common_cancel),
            onClick = onCancelClickListener,
            enabled = true
        )
        Spacer(modifier = Modifier.width(16.dp))
        TextButton(
            text = stringResource(R.string.common_apply),
            onClick = onAcceptClickListener,
            enabled = true
        )
    }
}

@Composable
private fun TopBar(onCrossClickListener: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 20.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.reader_power_title),
            style = AppTheme.typography.h6
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_cross),
            contentDescription = null,
            colorFilter = ColorFilter.tint(graphite4),
            modifier = Modifier.clickable(
                onClick = onCrossClickListener,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false)
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
fun ReaderPowerScreenPreview() {
    ReaderPowerScreen(ReaderPowerStore.State(), AppInsets(), {}, {}, {}, {}, {}, {})
}
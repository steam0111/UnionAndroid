package com.itrocket.union.readingMode.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.readingMode.presentation.store.ReadingModeStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BottomSheetDivider
import com.itrocket.union.ui.ImageButton
import com.itrocket.union.ui.ReadingModeTabs
import com.itrocket.union.ui.white

@Composable
fun ReadingModeScreen(
    state: ReadingModeStore.State,
    appInsets: AppInsets,
    onCameraClickListener: () -> Unit,
    onSettingsClickListener: () -> Unit,
    onManualInputClickListener: () -> Unit,
    onReadingModeTabClickListener: (ReadingModeTab) -> Unit
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
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSheetDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.reading_mode_title),
                    style = AppTheme.typography.h6,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(16.dp))
                ReadingModeTabs(
                    tabs = state.tabs,
                    selectedTab = state.selectedTab,
                    onClick = onReadingModeTabClickListener
                )
                Spacer(modifier = Modifier.height(16.dp))
                BottomBar(
                    onCameraClickListener = onCameraClickListener,
                    onSettingsClickListener = onSettingsClickListener,
                    onManualInputClickListener = onManualInputClickListener,
                    selectedTab = state.selectedTab
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
}

@Composable
private fun BottomBar(
    onCameraClickListener: () -> Unit,
    onSettingsClickListener: () -> Unit,
    onManualInputClickListener: () -> Unit,
    selectedTab: ReadingModeTab
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ImageButton(
            imageId = R.drawable.ic_settings,
            paddings = PaddingValues(12.dp),
            onClick = onSettingsClickListener,
            isEnabled = selectedTab == ReadingModeTab.RFID
        )
        Spacer(modifier = Modifier.width(16.dp))
        BaseButton(
            text = stringResource(id = R.string.reading_mode_manual_input),
            onClick = onManualInputClickListener,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        ImageButton(
            imageId = R.drawable.ic_camera,
            paddings = PaddingValues(12.dp),
            onClick = onCameraClickListener
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
fun ReadingModeScreenPreview() {
    ReadingModeScreen(ReadingModeStore.State(
        selectedTab = ReadingModeTab.RFID,
        tabs = ReadingModeTab.values().toList()
    ), AppInsets(), {}, {}, {}, {})
}
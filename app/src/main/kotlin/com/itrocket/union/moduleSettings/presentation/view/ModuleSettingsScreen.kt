package com.itrocket.union.moduleSettings.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.moduleSettings.presentation.store.ModuleSettingsStore
import com.itrocket.union.readerPower.domain.ReaderPowerInteractor
import com.itrocket.union.ui.*

@Composable
fun ModuleSettingsScreen(
    state: ModuleSettingsStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onDefineCursorClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onDropdownDismiss: () -> Unit,
    onDropdownItemClickListener: (String) -> Unit,
    onDropdownOpenClickListener: () -> Unit,
) {
    AppTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = appInsets.topInset.dp, bottom = appInsets.bottomInset.dp),
            topBar = {
                Toolbar(onBackClickListener = onBackClickListener)
            },
            bottomBar = {
                ButtonBottomBar(
                    text = stringResource(R.string.common_save),
                    onClick = onSaveClickListener
                )
            },
            content = {
                Content(
                    state = state,
                    onDefineCursorClickListener = onDefineCursorClickListener,
                    paddingValues = it,
                    onDropdownDismiss = onDropdownDismiss,
                    onDropdownItemClickListener = onDropdownItemClickListener,
                    onDropdownOpenClickListener = onDropdownOpenClickListener
                )
            }
        )
    }
}

@Composable
private fun Toolbar(onBackClickListener: () -> Unit) {
    BaseToolbar(
        title = stringResource(R.string.module_settings_title),
        startImageId = R.drawable.ic_arrow_back,
        onStartImageClickListener = onBackClickListener
    )
}

@Composable
private fun Content(
    state: ModuleSettingsStore.State,
    onDefineCursorClickListener: () -> Unit,
    onDropdownDismiss: () -> Unit,
    onDropdownItemClickListener: (String) -> Unit,
    onDropdownOpenClickListener: () -> Unit,
    paddingValues: PaddingValues,
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.choose_power_of_reader),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = AppTheme.typography.body1,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(24.dp))
        DefineCursorComponent(
            onDefineCursorClickListener = onDefineCursorClickListener,
            keyCode = state.keyCode,
            waitDefine = state.isDefineWait
        )
        Spacer(modifier = Modifier.height(24.dp))
        SelectServiceComponent(
            state = state,
            onDropdownOpenClickListener = onDropdownOpenClickListener,
            onDropdownDismiss = onDropdownDismiss,
            onDropdownItemClickListener = onDropdownItemClickListener
        )

    }
}


@Composable
private fun DefineCursorComponent(
    onDefineCursorClickListener: () -> Unit,
    keyCode: Int,
    waitDefine: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onDefineCursorClickListener)
            .background(graphite2, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.ic_reader),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(AppTheme.colors.mainColor)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(
                if (waitDefine) {
                    R.string.module_settings_cursor_click
                } else {
                    R.string.module_settings_define_cursor
                }
            ).uppercase(),
            style = AppTheme.typography.body2,
            color = AppTheme.colors.mainColor
        )
        Spacer(modifier = Modifier.weight(1f))

        val keyCodeModifier = if (waitDefine) {
            Modifier
                .background(white, RoundedCornerShape(8.dp))
                .border(1.dp, AppTheme.colors.mainColor, RoundedCornerShape(8.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp)
        } else {
            Modifier
                .background(white, RoundedCornerShape(8.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp)
        }
        Text(
            text = keyCode.toString(),
            style = AppTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            color = if (waitDefine) {
                graphite6
            } else {
                graphite4
            },
            modifier = keyCodeModifier
        )
    }
}

@Composable
private fun SelectServiceComponent(
    state: ModuleSettingsStore.State,
    onDropdownDismiss: () -> Unit,
    onDropdownItemClickListener: (String) -> Unit,
    onDropdownOpenClickListener: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        DropdownMenuItem(
            onClick = onDropdownOpenClickListener,
            modifier = Modifier.background(graphite2)
        ) {
            val defaultService = if (!state.defaultService.isNullOrBlank()) {
                state.defaultService
            } else {
                stringResource(R.string.select_service_hint)
            }
            Text(text = defaultService, style = AppTheme.typography.body2)
        }
        DropdownMenu(
            expanded = state.dropdownExpanded,
            onDismissRequest = onDropdownDismiss,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            state.services.forEach {
                DropdownMenuItem(
                    onClick = { onDropdownItemClickListener(it) },
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Text(text = it, style = AppTheme.typography.body2)
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
fun ModuleSettingsScreenPreview() {
    ModuleSettingsScreen(
        ModuleSettingsStore.State(),
        AppInsets(topInset = previewTopInsetDp),
        {},
        {},
        {},
        {},
        {},
        {},
    )
}
package com.itrocket.union.moduleSettings.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.union.R
import com.itrocket.union.ui.AppTheme
import com.itrocket.core.base.AppInsets
import com.itrocket.union.moduleSettings.presentation.store.ModuleSettingsStore
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ButtonBottomBar
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.graphite4
import com.itrocket.union.ui.graphite6
import com.itrocket.union.ui.psb6
import com.itrocket.union.ui.white

@Composable
fun ModuleSettingsScreen(
    state: ModuleSettingsStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onDefineCursorClickListener: () -> Unit,
    onSaveClickListener: () -> Unit,
    onDropdownDismiss: () -> Unit,
    onDropdownItemClickListener: (String) -> Unit,
    onDropdownOpenClickListener: () -> Unit
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
            modifier = Modifier.size(24.dp)
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
            color = psb6
        )
        Spacer(modifier = Modifier.weight(1f))

        val keyCodeModifier = if (waitDefine) {
            Modifier
                .background(white, RoundedCornerShape(8.dp))
                .border(1.dp, psb6, RoundedCornerShape(8.dp))
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
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        DropdownMenuItem(onClick = onDropdownOpenClickListener) {
            Text(text = state.defaultService, style = AppTheme.typography.body2)
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
        {}
    )
}
package com.itrocket.union.syncAll.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.syncAll.presentation.store.SyncAllStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ButtonWithLoader
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.white

@Composable
fun SyncAllScreen(
    state: SyncAllStore.State,
    appInsets: AppInsets,
    onSyncButtonClicked: () -> Unit,
    onBackClickListener: () -> Unit
) {
    AppTheme {
        Column(modifier = Modifier.padding(top = appInsets.topInset.dp)) {
            Toolbar(onBackClickListener)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = appInsets.topInset.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonWithLoader(
                    onClick = onSyncButtonClicked,
                    modifier = Modifier,
                    isEnabled = true,
                    isLoading = state.isLoading
                ) {
                    Text(text = "Синхронизировать", color = white)
                }
            }
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit
) {
    BaseToolbar(
        title = stringResource(id = R.string.sync),
        startImageId = R.drawable.ic_cross,
        onStartImageClickListener = onBackClickListener,
        backgroundColor = psb1,
        textColor = white
    )
}

@Preview(name = "светлая тема экран - 6.3 (3040x1440)", showSystemUi = true, device = Devices.PIXEL_4_XL, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "темная тема экран - 4,95 (1920 × 1080)", showSystemUi = true, device = Devices.NEXUS_5, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun SyncAllScreenPreview() {
    SyncAllScreen(SyncAllStore.State(), AppInsets(), {}, {})
}
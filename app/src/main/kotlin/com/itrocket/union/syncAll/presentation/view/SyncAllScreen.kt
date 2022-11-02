package com.itrocket.union.syncAll.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_api.entity.SyncEvent.Measured
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.alertType.AlertType
import com.itrocket.union.syncAll.presentation.store.SyncAllStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ButtonWithContent
import com.itrocket.union.ui.ButtonWithLoader
import com.itrocket.union.ui.ConfirmAlertDialog
import com.itrocket.union.ui.grey2
import com.itrocket.union.ui.white
import kotlin.time.Duration.Companion.seconds

@Composable
fun SyncAllScreen(
    state: SyncAllStore.State,
    appInsets: AppInsets,
    onSyncButtonClicked: () -> Unit,
    onBackClickListener: () -> Unit,
    onClearButtonClicked: () -> Unit,
    onAuthButtonClicked: () -> Unit,
    onConfirmSyncClickListener: () -> Unit,
    onDismissSyncClickListener: () -> Unit,
    onConfirmLogoutClickListener: () -> Unit,
    onDismissLogoutClickListener: () -> Unit,
    onDismissClearClickListener: () -> Unit,
    onConfirmClearDbClickListener: () -> Unit,
) {
    AppTheme {
        Column(
            modifier = Modifier
                .padding(top = appInsets.topInset.dp, bottom = appInsets.bottomInset.dp)
        ) {
            Toolbar(onBackClickListener)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 8.dp,
                        end = 8.dp
                    )
            ) {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    ButtonWithLoader(
                        onClick = onSyncButtonClicked,
                        modifier = Modifier,
                        isEnabled = true,
                        isLoading = state.isLoading
                    ) {
                        Text(text = stringResource(R.string.to_sync), color = white)
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    ButtonWithContent(
                        onClick = onClearButtonClicked,
                        modifier = Modifier,
                        isEnabled = true,
                        content = {
                            Text(
                                text = stringResource(R.string.to_clear_db), color = white
                            )
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    ButtonWithContent(
                        onClick = onAuthButtonClicked,
                        modifier = Modifier,
                        isEnabled = true,
                        content = {
                            Text(
                                text = stringResource(R.string.to_auth), color = white
                            )
                        }
                    )
                }
                itemsIndexed(state.syncEvents, key = { _, item ->
                    item.id
                }) { index, item ->
                    Spacer(modifier = Modifier.height(4.dp))
                    SyncEvent(item)
                }
            }
        }
        when (state.dialogType) {
            AlertType.SYNC -> ConfirmAlertDialog(
                onDismiss = onDismissSyncClickListener,
                onConfirmClick = onConfirmSyncClickListener,
                textRes = R.string.document_menu_dialog_sync_title,
                confirmTextRes = R.string.common_yes,
                dismissTextRes = R.string.common_no
            )
            AlertType.LOGOUT -> ConfirmAlertDialog(
                onDismiss = onDismissLogoutClickListener,
                onConfirmClick = onConfirmLogoutClickListener,
                textRes = R.string.document_menu_dialog_logout_title,
                confirmTextRes = R.string.common_yes,
                dismissTextRes = R.string.common_no
            )
            AlertType.CLEAR_DB -> ConfirmAlertDialog(
                onDismiss = onDismissClearClickListener,
                onConfirmClick = onConfirmClearDbClickListener,
                textRes = R.string.document_menu_dialog_clear_db_title,
                confirmTextRes = R.string.common_yes,
                dismissTextRes = R.string.common_no
            )
        }
    }
}

@Composable
private fun SyncEvent(syncEvent: SyncEvent) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = grey2,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(all = 8.dp)
    ) {
        Text(text = "Событие: ${syncEvent.name}", modifier = Modifier.fillMaxWidth())

        when (syncEvent) {
            is Measured -> {
                syncEvent.duration.toComponents { hours, minutes, seconds, nanoseconds ->
                    val millis = if (hours == 0L && minutes == 0 && seconds == 0) {
                        "${syncEvent.duration.inWholeMilliseconds}ms"
                    } else {
                        ""
                    }

                    Text(
                        text = "Время выполнения ${hours}h ${minutes}m ${seconds}s $millis",
                        modifier = Modifier.fillMaxWidth()
                    )
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
    )
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
fun SyncAllScreenPreview() {
    SyncAllScreen(SyncAllStore.State(
        syncEvents = buildList {
            add(SyncEvent.Info("Начало синхронизации", "0"))
            add(
                SyncEvent.Measured(
                    "1", "Завершение синхронизации", 3000.seconds
                )
            )
            add(
                SyncEvent.Measured(
                    "2", "Завершение синхронизации", 30000.seconds
                )
            )
            add(
                SyncEvent.Measured(
                    "3", "Завершение синхронизации", 3001.seconds
                )
            )
        }
    ), AppInsets(), {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
}
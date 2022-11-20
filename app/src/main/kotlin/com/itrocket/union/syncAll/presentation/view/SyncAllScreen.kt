package com.itrocket.union.syncAll.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_api.entity.SyncEvent.Measured
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.syncAll.presentation.store.SyncAllStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.HorizontalFilledIndicator
import com.itrocket.union.ui.OutlinedButton
import com.itrocket.union.ui.diagRed
import com.itrocket.union.ui.graphite3
import com.itrocket.union.ui.red5
import com.itrocket.union.ui.white
import kotlin.time.Duration.Companion.seconds

@Composable
fun SyncAllScreen(
    state: SyncAllStore.State,
    appInsets: AppInsets,
    onCrossClickListener: () -> Unit,
    onFinishClickListener: () -> Unit,
    onChangeVisibleLogClickListener: () -> Unit,
    onSyncClickListener: () -> Unit,
) {
    AppTheme {
        Scaffold(
            modifier = Modifier
                .background(white)
                .padding(
                    top = appInsets.topInset.dp,
                    bottom = appInsets.bottomInset.dp
                ),
            topBar = {
                Toolbar(
                    onCrossClickListener = onCrossClickListener,
                    lastSyncDate = state.lastDateSync
                )
            },
            bottomBar = {
                BottomBar(
                    isSyncLoading = state.isLoading,
                    isSyncFinished = state.isSyncFinished,
                    isShowLog = state.isShowLog,
                    onChangeVisibleLogClickListener = onChangeVisibleLogClickListener,
                    onFinishClickListener = onFinishClickListener,
                    onSyncClickListener = onSyncClickListener
                )
            },
            content = {
                Content(state = state, paddingValues = it)
            })
    }
}

@Composable
private fun Content(state: SyncAllStore.State, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        if (state.isLoading || state.isSyncFinished) {
            Spacer(modifier = Modifier.height(12.dp))
            Title(state)
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalFilledIndicator(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth(),
                maxCount = state.allCount.toFloat(),
                count = state.syncedCount.toFloat()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${state.syncedCount}/${state.allCount}",
                style = TextStyle(color = AppTheme.colors.mainTextColor, fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (state.isShowLog) {
                LazyColumn {
                    items(state.syncEvents) {
                        Spacer(modifier = Modifier.height(8.dp))
                        SyncEvent(it)
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun Title(state: SyncAllStore.State) {
    when {
        state.isSyncFinished && state.isSyncSuccess -> {
            Text(
                text = stringResource(R.string.sync_all_success),
                style = AppTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )
        }
        state.isSyncFinished -> {
            Text(
                text = stringResource(R.string.sync_all_error),
                style = AppTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                color = diagRed
            )
        }
        else -> {
            Text(
                text = state.currentSyncTitle,
                style = TextStyle(color = AppTheme.colors.secondaryColor, fontSize = 12.sp),
            )
        }
    }
}

@Composable
private fun Toolbar(onCrossClickListener: () -> Unit, lastSyncDate: String) {
    BaseToolbar(
        title = lastSyncDate,
        startImageId = R.drawable.ic_cross,
        onStartImageClickListener = onCrossClickListener,
    )
}

@Composable
private fun BottomBar(
    isSyncLoading: Boolean,
    isSyncFinished: Boolean,
    isShowLog: Boolean,
    onChangeVisibleLogClickListener: () -> Unit,
    onFinishClickListener: () -> Unit,
    onSyncClickListener: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp)
            .background(white)
            .padding(16.dp)
    ) {
        when {
            isSyncFinished -> {
                BaseButton(
                    text = stringResource(R.string.common_finish),
                    onClick = onFinishClickListener,
                    modifier = Modifier.fillMaxWidth(),
                    isAllUppercase = false,
                )
                Spacer(modifier = Modifier.height(8.dp))
                LogButton(
                    isShowLog = isShowLog,
                    onChangeVisibleLogClickListener = onChangeVisibleLogClickListener
                )
            }
            isSyncLoading -> {
                LogButton(
                    isShowLog = isShowLog,
                    onChangeVisibleLogClickListener = onChangeVisibleLogClickListener
                )
            }
            else -> {
                BaseButton(
                    text = stringResource(R.string.sync),
                    onClick = onSyncClickListener,
                    modifier = Modifier.fillMaxWidth(),
                    isAllUppercase = false,
                )
            }
        }
    }
}

@Composable
private fun LogButton(isShowLog: Boolean, onChangeVisibleLogClickListener: () -> Unit) {
    val buttonLogText = stringResource(
        id = if (isShowLog) {
            R.string.sync_all_hide_log
        } else {
            R.string.sync_all_show_log
        }
    )
    OutlinedButton(
        text = buttonLogText,
        onClick = onChangeVisibleLogClickListener,
        outlineColor = graphite3,
        textColor = AppTheme.colors.mainTextColor,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun SyncEvent(syncEvent: SyncEvent) {
    val backgroundColor = if (syncEvent is SyncEvent.Error) {
        red5
    } else {
        white
    }
    val textColor = if (syncEvent is SyncEvent.Error) {
        white
    } else {
        AppTheme.colors.mainTextColor
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(8.dp))
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        val eventText = StringBuilder().append("Событие: ${syncEvent.name}")

        when (syncEvent) {
            is Measured -> {
                syncEvent.duration.toComponents { hours, minutes, seconds, nanoseconds ->
                    val millis = if (hours == 0L && minutes == 0 && seconds == 0) {
                        "${syncEvent.duration.inWholeMilliseconds}ms"
                    } else {
                        ""
                    }

                    eventText.append("\nВремя выполнения ${hours}h ${minutes}m ${seconds}s $millis")
                }
            }
        }

        Text(
            text = eventText.toString(),
            modifier = Modifier.fillMaxWidth(),
            style = AppTheme.typography.body2,
            color = textColor
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
fun SyncAllScreenPreview() {
    SyncAllScreen(SyncAllStore.State(
        allCount = 10,
        syncedCount = 3,
        isShowLog = true,
        syncEvents = buildList {
            add(SyncEvent.Info("Начало синхронизации", "0"))
            add(Measured("1", "Завершение синхронизации", 3000.seconds))
            add(Measured("2", "Завершение синхронизации", 30000.seconds))
            add(Measured("3", "Завершение синхронизации", 3001.seconds))
        }
    ), AppInsets(topInset = previewTopInsetDp), {}, {}, {}, {})
}
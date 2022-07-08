package com.itrocket.union.chooseAction.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.itrocket.union.chooseAction.presentation.store.ChooseActionStore
import com.itrocket.union.documents.domain.entity.ObjectType
import com.itrocket.union.ui.*

@Composable
fun ChooseActionScreen(
    state: ChooseActionStore.State,
    appInsets: AppInsets,
    onTypeClickListener: (ObjectType) -> Unit
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
                    text = stringResource(R.string.choose_action_title),
                    style = AppTheme.typography.h6,
                    modifier = Modifier.fillMaxWidth()
                )
                state.types.forEach {
                    Spacer(modifier = Modifier.height(16.dp))
                    BaseButton(
                        text = stringResource(it.textId),
                        onClick = {
                            onTypeClickListener(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = psb1
                    )
                }
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
fun ChooseActionScreenPreview() {
    ChooseActionScreen(ChooseActionStore.State(
        types = ObjectType.values().toList()
    ), AppInsets(), {})
}
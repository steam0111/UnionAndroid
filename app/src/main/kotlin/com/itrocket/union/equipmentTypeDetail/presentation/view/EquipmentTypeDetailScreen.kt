package com.itrocket.union.equipmentTypeDetail.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.equipmentTypeDetail.domain.entity.EquipmentTypeDetailDomain
import com.itrocket.union.equipmentTypeDetail.presentation.store.EquipmentTypeDetailStore
import com.itrocket.union.ui.*

@Composable
fun EquipmentTypeDetailScreen(
    state: EquipmentTypeDetailStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onBackClickListener = onBackClickListener
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        ) {
            Column(modifier = Modifier.padding(it)) {
                Spacer(modifier = Modifier.height(16.dp))
                ListInfoItem(listInfo = state.item.listInfo)
            }
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit
) {
    BaseToolbar(
        title = stringResource(id = R.string.equipment_type_title),
        startImageId = R.drawable.ic_cross,
        onStartImageClickListener = onBackClickListener
    )
}

@Composable
private fun ListInfoItem(listInfo: List<ObjectInfoDomain>) {
    LazyColumn {
        items(listInfo) { item ->
            ExpandedInfoField(
                label = item.title?.let { stringResource(id = it) }.orEmpty(),
                value = item.value ?: item.valueRes?.let { stringResource(id = it) }.orEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(
    name = "?????????????? ???????? ?????????? - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "???????????? ???????? ?????????? - 4,95 (1920 ?? 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "??????????????", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun EquipmentTypeDetailScreenPreview() {
    EquipmentTypeDetailScreen(
        EquipmentTypeDetailStore.State(
            item = EquipmentTypeDetailDomain(
                listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.equipment_type_title,
                        "??????????????????????????????????????????????"
                    ),
                    ObjectInfoDomain(
                        R.string.equipment_type_title,
                        "??????????????????????????????????????????????"
                    )
                )
            )
        ), AppInsets(topInset = previewTopInsetDp), {})
}
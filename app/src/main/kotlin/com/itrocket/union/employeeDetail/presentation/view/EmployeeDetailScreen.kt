package com.itrocket.union.employeeDetail.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itrocket.core.base.AppInsets
import com.itrocket.core.utils.previewTopInsetDp
import com.itrocket.union.R
import com.itrocket.union.accountingObjectDetail.domain.entity.EmployeeDetailDomain
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.employeeDetail.presentation.store.EmployeeDetailStore
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ExpandedInfoField
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded

@Composable
fun EmployeeDetailScreen(
    state: EmployeeDetailStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onSaveBtnClickListener: () -> Unit,
    onSearchClickListener: () -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onBackClickListener = onBackClickListener,
                    onSearchClickListener = onSearchClickListener
                )
            },
            bottomBar = {
                BottomBar(
                    onBtnClickListener = onSaveBtnClickListener
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
    onBackClickListener: () -> Unit,
    onSearchClickListener: () -> Unit
) {
    BaseToolbar(
        title = stringResource(id = R.string.employees_title),
        startImageId = R.drawable.ic_cross,
        onStartImageClickListener = onBackClickListener,
        backgroundColor = psb1,
        textColor = white,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_document_search),
                    contentDescription = null,
                    modifier = Modifier.clickableUnbounded(onClick = onSearchClickListener)
                )
            }
        }
    )
}

@Composable
private fun BottomBar(
    onBtnClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(16.dp)
    ) {
        BaseButton(
            text = stringResource(R.string.save),
            onClick = onBtnClickListener,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ListInfoItem(listInfo: List<ObjectInfoDomain>) {
    LazyColumn {
        items(listInfo) { item ->
            ExpandedInfoField(
                label = stringResource(id = item.title),
                value = item.value ?: item.valueRes?.let { stringResource(id = it) }.orEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(
    name = "светлая тема экран - 6.3 (3040x1440)",
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "темная тема экран - 4,95 (1920 × 1080)",
    showSystemUi = true,
    device = Devices.NEXUS_5,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun EmployeeDetailScreenPreview() {
    EmployeeDetailScreen(
        EmployeeDetailStore.State(
            item = EmployeeDetailDomain(
                listInfo =
                listOf(
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    ),
                    ObjectInfoDomain(
                        R.string.auth_main_title,
                        "таылватвлыавыалвыоалвыа"
                    )
                )
            )
        ), AppInsets(topInset = previewTopInsetDp), {}, {}, {})
}
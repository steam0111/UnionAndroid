package com.itrocket.union.reserveDetail.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itrocket.core.base.AppInsets
import com.itrocket.union.R
import com.itrocket.union.accountingObjects.domain.entity.ObjectInfoDomain
import com.itrocket.union.reserveDetail.presentation.store.ReserveDetailStore
import com.itrocket.union.reserves.domain.entity.ReservesDomain
import com.itrocket.union.ui.AppTheme
import com.itrocket.union.ui.BaseButton
import com.itrocket.union.ui.BaseToolbar
import com.itrocket.union.ui.ExpandedInfoField
import com.itrocket.union.ui.graphite2
import com.itrocket.union.ui.psb1
import com.itrocket.union.ui.white
import com.itrocket.utils.clickableUnbounded

@Composable
fun ReserveDetailScreen(
    state: ReserveDetailStore.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit,
    onReadingModeClickListener: () -> Unit,
    onDocumentSearchClickListener: () -> Unit,
    onDocumentAddClickListener: () -> Unit,
) {
    AppTheme {
        Scaffold(
            topBar = {
                Toolbar(
                    onBackClickListener = onBackClickListener,
                    onDocumentAddClickListener = onDocumentAddClickListener,
                    onDocumentSearchClickListener = onDocumentSearchClickListener
                )
            },
            bottomBar = {
                BottomBar(
                    onReadingModeClickListener = onReadingModeClickListener
                )
            },
            modifier = Modifier.padding(
                top = appInsets.topInset.dp,
                bottom = appInsets.bottomInset.dp
            )
        ) {
            Column(modifier = Modifier.padding(it)) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = state.reserve.title,
                    fontWeight = FontWeight.Medium,
                    style = AppTheme.typography.body1,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ListInfo(listInfo = state.reserve.listInfo)
            }
        }
    }
}

@Composable
private fun Toolbar(
    onBackClickListener: () -> Unit,
    onDocumentAddClickListener: () -> Unit,
    onDocumentSearchClickListener: () -> Unit
) {
    BaseToolbar(
        title = stringResource(id = R.string.reserves_title),
        startImageId = R.drawable.ic_cross,
        onStartImageClickListener = onBackClickListener,
        backgroundColor = psb1,
        textColor = white,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_document_add),
                    contentDescription = null,
                    modifier = Modifier.clickableUnbounded(onClick = onDocumentAddClickListener)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_document_search),
                    contentDescription = null,
                    modifier = Modifier.clickableUnbounded(onClick = onDocumentSearchClickListener)
                )
            }
        }
    )
}

@Composable
private fun BottomBar(
    onReadingModeClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(graphite2)
            .padding(16.dp)
    ) {
        BaseButton(
            text = stringResource(R.string.accounting_object_detail_reading_mode),
            onClick = onReadingModeClickListener,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ListInfo(listInfo: List<ObjectInfoDomain>) {
    LazyColumn {
        items(listInfo) {
            ExpandedInfoField(
                label = it.title,
                value = it.value,
                modifier = Modifier.fillMaxWidth()
            )
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
fun ReserveDetailScreenPreview() {
    ReserveDetailScreen(ReserveDetailStore.State(
        reserve = ReservesDomain(
            id = "1", title = "Авторучка «Зебра TR22»", isBarcode = true, listInfo =
            listOf(
                ObjectInfoDomain(
                    "Заводской номер",
                    "таылватвлыавыалвыоалвыа"
                ),
                ObjectInfoDomain(
                    "Инвентарный номер",
                    "таылватвлыавыалвыоалвыа"
                )
            ), itemsCount = 1200
        )
    ), AppInsets(), {}, {}, {}, {})
}